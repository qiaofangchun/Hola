//
// Created by Administrator on 2022/11/23.
//

#include "media-player.h"

MediaPlayer::MediaPlayer() {
    this->pPlayerCall = jni_player_call;
    // 复制 url
    this->url = (char *) (malloc(strlen(url) + 1));
    memcpy(this->url, url, strlen(url) + 1);
    pthread_mutex_init(&releaseMutex, NULL);
    pPlayStatus = new DZPlayStatus();
}

void MediaPlayer::player_call(JNIPlayerCall *jni_player_call){

}

void MediaPlayer::source(char *url){

}

void prepare(){

}

void MediaPlayer::play(){

}

void MediaPlayer::stop(){

}

void MediaPlayer::pause(){

}

void MediaPlayer::seek(uint64_t msec){

}