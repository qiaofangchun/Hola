package com.hola.app.music.core

class MediaProvider {
    //播放队列,不能为空,不设置主动抛错
    private var mQueue = ArrayList<MediaMetaData>()
    private val mListener = ArrayList<MediaQueueListener>()

    val dataSize: Int get() = mQueue.size

    fun addMedia(media: MediaMetaData) {
        mQueue.add(media)
        notifyChanged()
    }

    fun getMediaMetaData(index: Int): MediaMetaData? {
        if (mQueue.isEmpty()) {
            return null
        }
        return mQueue[index]
    }

    fun addMediaMetaData(media: List<MediaMetaData>) {
        mQueue.addAll(media)
        notifyChanged()
    }

    fun setMediaQueue(mediaQueue: List<MediaMetaData>) {
        mQueue.clear()
        addMediaMetaData(mediaQueue)
    }

    private fun notifyChanged() {
        mListener.forEach {
            it.notifyChanged()
        }
    }

    interface MediaQueueListener {
        fun notifyChanged()
    }
}