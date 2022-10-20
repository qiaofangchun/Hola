#include <jni.h>
#include <string>

extern "C" {
#include "libavcodec/codec.h"
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_hola_app_music_MainActivity_getFFmpegVersion(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(av_version_info());
}