//
// Created by Administrator on 2022/11/22.
//

#ifndef HOLA_MEDIA_PLAYER_H
#define HOLA_MEDIA_PLAYER_H

#include "play-status.h"

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
};

/**
 * 基类，公共内容
 */
class MediaCore {
public:
    /**
     * 解码器上下文
     */
    AVCodecContext *codec_ctx = NULL;
    /**
     * 回调 Java 层的 Call
     */
    //JNIPlayerCall *player_call = NULL;
    /**
     * 播放状态
     */
    //DZPlayStatus *play_status = NULL;
    /**
     * AVPacket 队列
     */
    //AVPacketQueue *packet_queue = NULL;
    /**
     * 当前流的角标（音频/视频/字幕）
     */
    int streamIndex = 0;
    /**
     * 整个视频的时长
     */
    int duration = 0;
    /**
     * 当前播放的时长
     */
    double play_time = 0;

    /**
     * 上次更新的时间，主要用于控制回调到 Java 层的频率
     */
    double last_update_time = 0;

    /**
     * 时间机
     */
    AVRational rational;

    /**
     * seek 时的 mutex
     */
    pthread_mutex_t seekMutex;

public:
    MediaCore(int streamIndex, PlayStatus *pPlayStatus, JNIPlayerCall *pPlayerCall);

    ~MediaCore();

public:
    /**
     * 播放方法，纯虚函数
     */
    virtual void play() = 0;

    /**
     * 解析公共的解码器上下文
     */
    virtual void analysisStream(ThreadType threadType, AVFormatContext *pFormatContext);

    /**
     * 准备解析数据过程中出错的回调
     * @param threadType 线程类型
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    void callPlayerJniError(ThreadType threadType, int errorCode, const char *errorMsg);

    /**
     * 释放资源
     */
    void release();

    /**
     * seek到当前时间
     * @param seconds 秒
     */
    virtual void seek(uint64_t seconds);
};

#endif //HOLA_MEDIA_PLAYER_H
