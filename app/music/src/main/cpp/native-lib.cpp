#include <jni.h>
#include <string>
#include <android/native_window_jni.h>
#include <zconf.h>

extern "C"{
#include "libavcodec/codec.h"
#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libavutil/imgutils.h"
#include "libswscale/swscale.h"
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hola_app_music_VideoPlayer_native_1version(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(av_version_info());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_hola_app_music_VideoPlayer_native_1start(JNIEnv *env, jobject thiz, jobject surface,
                                                  jstring file_path) {
    // 得到Android用于绘制的NativeWindow
    ANativeWindow *native_window = ANativeWindow_fromSurface(env, surface);
    // 将Java字符串转换为C可用的字符串
    const char *path = env->GetStringUTFChars(file_path, 0);
    // 初始化网络
    avformat_network_init();
    // 构建总上下文
    AVFormatContext *format_ctx = avformat_alloc_context();
    // 配置信息
    AVDictionary *opts = NULL;
    av_dict_set(&opts, "timeout", "3000000", 0);
    // 打开视频，如果打开失败就结束
    if (avformat_open_input(&format_ctx, path, NULL, &opts)) {
        return;
    }

    // 解析出视频流
    avformat_find_stream_info(format_ctx, NULL);
    // 获取视频流对应的索引
    int video_stream_index = -1;
    for (int i = 0; i < format_ctx->nb_streams; ++i) {
        if (format_ctx->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            video_stream_index = i;
            break;
        }
    }

    // 获取视频流的解码参数（宽高等信息）
    AVCodecParameters *codec_par = format_ctx->streams[video_stream_index]->codecpar;
    // 获取视频流的解码器
    const AVCodec *de_codec = avcodec_find_decoder(codec_par->codec_id);
    // 获得解码器上下文
    AVCodecContext *codec_ctx = avcodec_alloc_context3(de_codec);
    // 将解码器参数复制到解码上下文(因为解码上下文目前还没有解码器参数)
    avcodec_parameters_to_context(codec_ctx, codec_par);
    // 进行解码
    avcodec_open2(codec_ctx, de_codec, NULL);
    // 因为YUV数据被封装在了AVPacket中，因此我们需要用AVPacket去获取数据
    AVPacket *packet = av_packet_alloc();
    // 获取转换上下文（把解码后的YUV数据转换为RGB数据才能在屏幕上显示）
    SwsContext *sws_ctx = sws_getContext(codec_ctx->width, codec_ctx->height, codec_ctx->pix_fmt,
                                         codec_ctx->width, codec_ctx->height, AV_PIX_FMT_RGBA,
                                         SWS_BILINEAR, 0, 0, 0);
    // 设置NativeWindow绘制的缓冲区
    ANativeWindow_setBuffersGeometry(native_window, codec_ctx->width, codec_ctx->height,
                                     WINDOW_FORMAT_RGBA_8888);
    ANativeWindow_Buffer out_buf;

    //计算出转换为RGB所需要的容器的大小
    //接收的容器
    uint8_t *dst_data[4];
    //每一行的首地址（R、G、B、A四行）
    int dst_line_size[4];
    //进行计算
    av_image_alloc(dst_data, dst_line_size, codec_ctx->width, codec_ctx->height,
                   AV_PIX_FMT_RGBA, 1);
    // 从视频流中读数据包，返回值小于0的时候表示读取完毕
    while (av_read_frame(format_ctx, packet) >= 0) {
        // 将取出的数据发送出来
        avcodec_send_packet(codec_ctx, packet);
        // 接收发送出来的数据
        AVFrame *frame = av_frame_alloc();
        int result = avcodec_receive_frame(codec_ctx, frame);
        // 如果读取失败就重新读
        if (result == AVERROR(EAGAIN)) {
            continue;
        } else if (result < 0) {
            //如果到末尾了就结束循环读取
            break;
        }
        // 将取出的数据放到之前定义的RGB目标容器中
        sws_scale(sws_ctx, frame->data, frame->linesize, 0, frame->height,
                  dst_data, dst_line_size);

        // 锁住画布，随后进行渲染
        ANativeWindow_lock(native_window, &out_buf, NULL);
        // 渲染画面
        uint8_t *first_window = static_cast<uint8_t *>(out_buf.bits);
        uint8_t *src_data = dst_data[0];
        //拿到每行有多少个RGBA字节
        int dst_stride = out_buf.stride * 4;
        int src_line_size = dst_line_size[0];
        //循环遍历所得到的缓冲区数据
        for (int i = 0; i < out_buf.height; ++i) {
            // 用内存拷贝，来进行渲染
            memcpy(first_window + i * dst_stride, src_data + i * src_line_size, dst_stride);
        }
        // 绘制完解锁画布
        ANativeWindow_unlockAndPost(native_window);
        //40000微秒之后解析下一帧(这个是根据视频的帧率来设置的，我这播放的视频帧率是25帧/秒)
        usleep(1000 * 40);
        //释放资源
        av_frame_free(&frame);
        av_packet_free(&packet);
    }
    env->ReleaseStringUTFChars(file_path, path);
}