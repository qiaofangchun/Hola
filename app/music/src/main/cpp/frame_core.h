//
// Created by admin on 2022/11/25.
//

#ifndef HOLA_FRAME_CORE_H
#define HOLA_FRAME_CORE_H

#include <pthread.h>
#include "media_core.h"
#include "audio_core.h"
#include "play_status.h"
#include "jni_player_call.h"
#include "consts.h"

extern "C" {
#include "libavutil/imgutils.h"
#include "libswscale/swscale.h"
#include "libavutil/time.h"
#include "libavcodec/bsf.h"
};

class FrameCore : public MediaCore {
public:
    AVFrame *pFrameYUV420P = NULL;
    uint8_t *pFrameBuffer = NULL;
    SwsContext *pSwsContext = NULL;
    AudioCore *pAudio = NULL;
    double delayTime = 0;
    double defaultDelayTime = 0.04;
    bool supportStiffCodec = false;
    AVBSFContext *pBSFContext;
public:
    FrameCore(int videoStreamIndex, PlayStatus *pPlayStatus, JNIPlayerCall *pPlayerCall,
              AudioCore *pAudio);

    ~FrameCore();

public:
    void play();

    virtual void analysisStream(ThreadMode mode, AVFormatContext *pFormatContext);

    void release();

    double getFrameSleepTime(int64_t pts);
};


#endif //NDK_DAY03_DZVIDEO_H
