package com.hola.app.music

import android.support.v4.media.MediaBrowserCompat
import com.hola.arch.domain.UseCase
import com.hola.common.utils.Logcat
import com.hola.media.core.MediaControllerHelper
import com.hola.media.core.MediaDataSubscribeCallback
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainUseCase : UseCase() {
    companion object {
        private const val TAG = "MainUseCase"
    }

    suspend fun getMediaData(mediaId: String) = flow {
        emit(suspendCancellableCoroutine { continuation ->
            val callback = object : MediaDataSubscribeCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    continuation.resume(children)
                }

                override fun onError(parentId: String) {
                    continuation.resumeWithException(Exception("children load error."))
                }
            }
            continuation.invokeOnCancellation {
                MediaControllerHelper.unsubscribeMediaData(mediaId, callback)
            }
            MediaControllerHelper.subscribeMediaData(mediaId, callback)
        })
    }
}