//
// Created by Administrator on 2022/11/22.
//

#ifndef HOLA_MEDIA_PLAYER_H
#define HOLA_MEDIA_PLAYER_H

#include <jni.h>
#include <pthread.h>
//#include "JNIPlayerCall.h"
#include "audio-core.h"
//#include "AVPacketQueue.h"
#include "consts.h"
#include "frame-core.h"
#include "unistd.h"

extern "C" {
#include "libavformat/avformat.h"
#include "libswresample/swresample.h"
}

class MediaPlayer {
public:
    JNIPlayerCall *pPlayerCall = NULL;
    char *url = NULL;
    pthread_t preparedThread;
    AVFormatContext *av_format_context = NULL;
    DZAudio *audio = NULL;
    DZVideo *pVideo = NULL;
    // 跟退出相关，为了防止准备过程中退出，导致异常终止
    pthread_mutex_t releaseMutex;
    DZPlayStatus *pPlayStatus;

public:
    MediaPlayer();

    ~MediaPlayer();

    void source(char *url);

    void player_call(JNIPlayerCall *jni_player_call)

    void prepare();

    void play();

    void stop();

    void pause();

    void seek(uint64_t msec);
};

#endif //HOLA_MEDIA_PLAYER_H
