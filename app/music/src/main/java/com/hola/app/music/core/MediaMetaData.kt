package com.hola.app.music.core

data class MediaMetaData(
    //地址
    var url: String,
    //歌名
    var name: String,
    //作者
    var author: String? = null,
    //所属专辑
    var album: String? = null,
    var albumInfo: String? = null,
    //专辑封面
    var albumPic: String? = null,
)