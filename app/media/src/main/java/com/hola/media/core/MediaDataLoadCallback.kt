package com.hola.media.core

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat

abstract class MediaDataLoadCallback : MediaBrowserCompat.SubscriptionCallback() {
    final override fun onChildrenLoaded(
        parentId: String,
        children: MutableList<MediaBrowserCompat.MediaItem>
    ) {
        onLoaded(parentId, children)
    }

    final override fun onChildrenLoaded(
        parentId: String,
        children: MutableList<MediaBrowserCompat.MediaItem>,
        options: Bundle
    ) {
        onLoaded(parentId, children, options)
    }

    final override fun onError(parentId: String) {
        onFailed(parentId)
    }

    final override fun onError(parentId: String, options: Bundle) {
        onFailed(parentId)
    }

    abstract fun onLoaded(
        parentId: String,
        children: MutableList<MediaBrowserCompat.MediaItem>,
        options: Bundle? = null
    )

    abstract fun onFailed(parentId: String, options: Bundle? = null)
}