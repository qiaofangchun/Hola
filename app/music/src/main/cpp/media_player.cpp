//
// Created by 曾辉 on 2019-06-05.
//
#include "media_player.h"

MediaPlayer::MediaPlayer(JNIPlayerCall *jni_player_call, const char *url) {
    this->pPlayerCall = jni_player_call;
    // 复制 url
    this->url = (char *) (malloc(strlen(url) + 1));
    memcpy(this->url, url, strlen(url) + 1);
    pthread_mutex_init(&releaseMutex, NULL);
    pPlayStatus = new PlayStatus();
}

void *decodeAudioThread(void *data) {
    MediaPlayer *fFmpeg = (MediaPlayer *) data;
    fFmpeg->preparedAudio(THREAD_CHILD);
    pthread_exit(0);
}

void MediaPlayer::prepared() {
    preparedAudio(THREAD_MAIN);
}

void MediaPlayer::prepared_async() {
    pthread_create(&preparedThread, NULL, decodeAudioThread, this);
}

void MediaPlayer::preparedAudio(ThreadMode mode) {
    pthread_mutex_lock(&releaseMutex);

    // av_register_all: 作用是初始化所有组件，只有调用了该函数，才能使用复用器和编解码器（源码）
    avformat_network_init();

    int format_open_res = avformat_open_input(&format_ctx, url, NULL, NULL);
    if (format_open_res < 0) {
        LOGE("Can't open url : %s, %s", url, av_err2str(format_open_res));
        releasePreparedRes(mode, format_open_res, av_err2str(format_open_res));
        return;
    }

    int find_stream_info_res = avformat_find_stream_info(format_ctx, NULL);
    if (find_stream_info_res < 0) {
        LOGE("Can't find stream info url : %s, %s", url, av_err2str(find_stream_info_res));
        releasePreparedRes(mode, find_stream_info_res, av_err2str(find_stream_info_res));
        return;
    }

    int audio_stream_index = av_find_best_stream(format_ctx, AVMEDIA_TYPE_AUDIO, -1, -1, NULL,
                                                 0);
    if (audio_stream_index < 0) {
        LOGE("Can't find audio stream info url : %s", url);
        releasePreparedRes(mode, FIND_AUDIO_STREAM_ERROR_CODE, "Can't find audio stream info url.");
        return;
    }
    audio = new AudioCore(audio_stream_index, pPlayStatus, pPlayerCall);
    audio->analysisStream(mode, format_ctx);

    int frame_stream_index = av_find_best_stream(format_ctx, AVMEDIA_TYPE_VIDEO, -1, -1, NULL,
                                                 0);
    if (frame_stream_index < 0) {
        LOGE("Can't find video stream info url : %s", url);
        releasePreparedRes(mode, FIND_VIDEO_STREAM_ERROR_CODE, "Can't find video stream info url.");
        return;
    }
    pVideo = new FrameCore(frame_stream_index, pPlayStatus, pPlayerCall, audio);
    pVideo->analysisStream(mode, format_ctx);

    pPlayerCall->onCallPrepared(mode);
    pthread_mutex_unlock(&releaseMutex);
}

MediaPlayer::~MediaPlayer() {

}

void *threadDecodeFrame(void *data) {
    MediaPlayer *pFFmpeg = (MediaPlayer *) (data);

    while (pFFmpeg->pPlayStatus != NULL && !pFFmpeg->pPlayStatus->isExit) {
        AVPacket *packet = av_packet_alloc();
        // 提取每一帧的音频流
        if (av_read_frame(pFFmpeg->format_ctx, packet) >= 0) {
            // 必须要是音频流
            if (pFFmpeg->audio->stream_index == packet->stream_index) {
                pFFmpeg->audio->packet_queue->push(packet);
            } else if (pFFmpeg->pVideo->stream_index == packet->stream_index) {
                pFFmpeg->pVideo->packet_queue->push(packet);
            } else {
                av_packet_free(&packet);
            }
        } else {
            av_packet_free(&packet);
        }
    }
    pthread_exit((void *) 1);
}

void MediaPlayer::start() {
    if (audio == NULL) {
        LOGE("DZAudio is null , prepared may be misleading");
        return;
    }
    if (pVideo == NULL) {
        LOGE("DZVideo is null , prepared may be misleading");
        return;
    }
    this->decodeFrame();
    audio->play();
    pVideo->play();
}

void MediaPlayer::onPause() {
    if (audio != NULL) {
        audio->pause();
    }

    if (pPlayStatus != NULL) {
        pPlayStatus->isPause = true;
    }
}

void MediaPlayer::onResume() {
    if (audio != NULL) {
        audio->resume();
    }

    if (pPlayStatus != NULL) {
        pPlayStatus->isPause = false;
    }
}

void MediaPlayer::release() {

    pthread_mutex_lock(&releaseMutex);

    if (audio->play_status->isExit) {
        return;
    }

    audio->play_status->isExit = true;

    if (audio != NULL) {
        audio->release();
        delete audio;
        audio = NULL;
    }

    if (pVideo != NULL) {
        pVideo->release();
        delete pVideo;
        pVideo = NULL;
    }

    if (format_ctx != NULL) {
        avformat_close_input(&format_ctx);
        avformat_free_context(format_ctx);
        format_ctx = NULL;
    }

    pPlayerCall = NULL;
    free(url);

    pthread_mutex_unlock(&releaseMutex);
    pthread_mutex_destroy(&releaseMutex);
}

void MediaPlayer::releasePreparedRes(ThreadMode mode, int errorCode, const char *errorMsg) {
    pthread_mutex_unlock(&releaseMutex);
    if (format_ctx != NULL) {
        avformat_close_input(&format_ctx);
        avformat_free_context(format_ctx);
        format_ctx = NULL;
    }

    if (pPlayerCall != NULL) {
        pPlayerCall->onCallError(mode, errorCode, errorMsg);
    }

    pthread_mutex_destroy(&releaseMutex);
    free(url);
}

void MediaPlayer::seek(uint64_t seconds) {
    if (pPlayStatus != NULL) {
        pPlayStatus->isSeek = true;
    }

    if (seconds >= 0) {
        int64_t rel = seconds * AV_TIME_BASE;
        av_seek_frame(format_ctx, -1, rel, AVSEEK_FLAG_BACKWARD);
    }

    if (pVideo != NULL) {
        pVideo->seek(seconds);
    }

    if (audio != NULL) {
        audio->seek(seconds);
    }

    if (pPlayStatus != NULL) {
        pPlayStatus->isSeek = false;
    }
}

void MediaPlayer::decodeFrame() {
    pthread_t decodeFrameThreadT;
    pthread_create(&decodeFrameThreadT, NULL, threadDecodeFrame, this);
}

