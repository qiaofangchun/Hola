package com.hola.app.music

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import com.hola.arch.domain.UseCase
import com.hola.media.core.MediaControllerHelper
import com.hola.media.core.MediaDataLoadCallback
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainUseCase : UseCase() {
    companion object {
        private const val TAG = "MainUseCase"
    }

    suspend fun getMediaData(mediaId: String) = flow {
        emit(suspendCancellableCoroutine { continuation ->
            val callback = object : MediaDataLoadCallback() {
                override fun onLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>,
                    options: Bundle?
                ) {
                    continuation.resume(children)
                    MediaControllerHelper.unsubscribeMediaData(mediaId, this)
                }

                override fun onFailed(parentId: String, options: Bundle?) {
                    continuation.resumeWithException(Exception("children load error."))
                    MediaControllerHelper.unsubscribeMediaData(mediaId, this)
                }
            }
            continuation.invokeOnCancellation {
                MediaControllerHelper.unsubscribeMediaData(mediaId, callback)
            }
            MediaControllerHelper.subscribeMediaData(mediaId, callback)
        })
    }
}