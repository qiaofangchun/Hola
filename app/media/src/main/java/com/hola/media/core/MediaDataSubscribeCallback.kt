package com.hola.media.core

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat

abstract class MediaDataSubscribeCallback : MediaBrowserCompat.SubscriptionCallback() {
    override fun onChildrenLoaded(
        parentId: String,
        children: MutableList<MediaBrowserCompat.MediaItem>,
        options: Bundle
    ) {
        onChildrenLoaded(parentId, children)
    }

    abstract override fun onChildrenLoaded(
        parentId: String,
        children: MutableList<MediaBrowserCompat.MediaItem>
    )

    override fun onError(parentId: String, options: Bundle) {
        onError(parentId)
    }

    abstract override fun onError(parentId: String)
}