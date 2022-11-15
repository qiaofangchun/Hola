#include <jni.h>
#include <string>
#include <android/native_window_jni.h>
#include <android/log.h>
#include <zconf.h>

extern "C" {
#include "libavcodec/codec.h"
#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libavutil/imgutils.h"
#include "libswscale/swscale.h"
#include "libswresample/swresample.h"
#include "unistd.h"
}
#define LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"LC",FORMAT,##__VA_ARGS__);
extern "C"
JNIEXPORT jstring JNICALL
Java_com_hola_app_music_VideoPlayer_native_1version(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(av_version_info());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_hola_app_music_MusicPlayer_n_1start(JNIEnv *env, jobject thiz, jstring path) {
    // 转换为c可用的字符串
    const char *url = env->GetStringUTFChars(path, 0);

    // 初始化网络
    avformat_network_init();

    AVFormatContext *format_ctx = avformat_alloc_context();
    // 1.打开文件
    int open_res = avformat_open_input(&format_ctx, url, NULL, NULL);
    if (open_res != 0) {
        // todo 文件打开失败，需要回调到Java层
        LOGE("open input error. code=%d", open_res)
        //goto _av_release;
        return;
    }
    // 2.解析出音/视频流到AVFormatContext中
    int find_res = avformat_find_stream_info(format_ctx, NULL);
    if (find_res < 0) {
        // todo 解析失败，需要回调到Java层
        LOGE("find stream info error. code=%d", open_res)
        //goto _av_release;
        return;
    }
    // 获取流索引
    int stream_index = av_find_best_stream(format_ctx, AVMediaType::AVMEDIA_TYPE_AUDIO,
                                           -1, -1, NULL, 0);
    // 根据流索引得到解码参数
    AVCodecParameters *codec_par = format_ctx->streams[stream_index]->codecpar;
    // 获得解码器
    const AVCodec *codec = avcodec_find_decoder(codec_par->codec_id);
    if (codec == NULL) {
        // todo 解码失败，需要回调到Java层
        LOGE("find decoder error.")
        //goto _av_release;
        return;
    }
    // 根据解码器创建一个AVCodecContext
    AVCodecContext *codec_ctx = avcodec_alloc_context3(codec);
    if (codec_ctx == NULL) {
        // todo 解码失败，需要回调到Java层
        LOGE("create codec context error.")
        //goto _av_release;
        return;
    }
    // 对AVCodecContext进行内容填充
    int par_copy_res = avcodec_parameters_to_context(codec_ctx, codec_par);
    if (par_copy_res < 0) {
        // todo 解码失败，需要回调到Java层
        LOGE("codec parameters to context error. code=%d", par_copy_res)
        //goto _av_release;
        return;
    }
    // 打开解码器
    int open_codec_res = avcodec_open2(codec_ctx, codec, NULL);
    if (open_codec_res != 0) {
        LOGE("codec open error. code=%d", open_codec_res)
        //goto _av_release;
        return;
    }

    // 采样
    AVChannelLayout out_ch_layout = AV_CHANNEL_LAYOUT_STEREO;
    enum AVSampleFormat out_sample_fmt = AVSampleFormat::AV_SAMPLE_FMT_S16;
    int out_sample_rate = AUDIO_SAMPLE;
    AVChannelLayout in_ch_layout = codec_par->ch_layout;
    enum AVSampleFormat in_sample_fmt = codec_ctx->sample_fmt;
    int in_sample_rate = codec_par->sample_rate;
    SwrContext *swr_ctx = swr_alloc();
    int swr_set_opts_res = swr_alloc_set_opts2(&swr_ctx,
                                               &out_ch_layout, out_sample_fmt, out_sample_rate,
                                               &in_ch_layout, in_sample_fmt, in_sample_rate,
                                               0, NULL);
    if (swr_set_opts_res != 0) {
        LOGE("set swr context opts error. code=%d", swr_set_opts_res)
        return;
    }
    int swr_init_res = swr_init(swr_ctx);
    if (swr_init_res < 0) {
        LOGE("swr init error. code=%d", swr_init_res)
        return;
    }

    //
    int data_size = av_samples_get_buffer_size(NULL,
                                               codec_par->ch_layout.nb_channels,
                                               codec_par->frame_size,
                                               out_sample_fmt, 0);
    auto *out_resample_buf = malloc(data_size);

    LOGE("codec par----sample_rate:%d channel_layout:%d", codec_par->sample_rate,
         codec_par->ch_layout.nb_channels);
    AVPacket *packet = av_packet_alloc();
    AVFrame *frame = av_frame_alloc();

    int index = 0;
    // 解析流
    while (av_read_frame(format_ctx, packet) >= 0) {
        int send_res = avcodec_send_packet(codec_ctx, packet);
        if (send_res != 0) {
            LOGE("send packet error. code%d", send_res);
            continue;
        }
        int receive_res = avcodec_receive_frame(codec_ctx, frame);
        if (receive_res != 0) {
            LOGE("receive frame error. code%d", send_res);
            continue;
        }
        if (stream_index == packet->stream_index) {
            LOGE("read frame:%d", index);
            //
            swr_convert(swr_ctx, (uint8_t **) out_resample_buf, frame->nb_samples,
                        (const uint8_t **) frame->data, frame->nb_samples);

            memccpy(, out_resample_buf, data_size);


            index++;

        }

        av_packet_unref(packet);
        av_frame_unref(frame);
    }

    _av_release:
    LOGE("start release")

    // 释放SwrContext
    swr_close(swr_ctx);
    swr_free(&swr_ctx);
    // 释放AVFrame
    av_frame_free(&frame);
    // AVPacket
    av_packet_free(&packet);
    // 释放AVCodecContext
    avcodec_close(codec_ctx);
    avcodec_free_context(&codec_ctx);
    // 释放AVFormatContext
    avformat_close_input(&format_ctx);
    avformat_free_context(format_ctx);
    // 释放网络
    avformat_network_deinit();
    // 释放文件路径
    env->ReleaseStringUTFChars(path, url);
}