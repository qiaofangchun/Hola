//
// Created by admin on 2022/11/25.
//

#ifndef HOLA_AUDIO_CORE_H
#define HOLA_AUDIO_CORE_H

#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "media_core.h"
#include "packet_queue.h"
#include "jni_player_call.h"
#include "consts.h"

extern "C" {
#include "libavformat/avformat.h"
#include "libavutil/frame.h"
#include "libswresample/swresample.h"
#include "libavutil/channel_layout.h"
#include "libavutil/rational.h"
#include "libavutil/time.h"
};

class AudioCore : public MediaCore {
public:
    pthread_t playThreadT;
    uint8_t *convertOutBuffer = NULL;
    SwrContext *swrContext = NULL;
    SLPlayItf slPlayItf = NULL;
    SLObjectItf pPlayer = NULL;
    SLObjectItf mixObj = NULL;
    SLObjectItf engineObj = NULL;
    SLAndroidSimpleBufferQueueItf androidBufferQueueItf;
    int frameBufferSize = 0;

public:
    AudioCore(int audioStreamIndex, PlayStatus *pPlayStatus, JNIPlayerCall *pPlayerCall);

    ~AudioCore();

    void init();

    void analysisStream(ThreadMode mode, AVFormatContext *pFormatContext);

    void resample();

    void play();

    void pause();

    void resume();

    void stop();

    void release();
};


#endif //HOLA_AUDIO_CORE_H
