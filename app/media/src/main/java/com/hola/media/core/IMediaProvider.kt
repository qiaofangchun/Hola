package com.hola.media.core

interface IMediaProvider {
    fun search(key: String)
    fun getFM()
    fun getAlbum(singerName: String)
}