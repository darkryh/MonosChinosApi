package com.ead.lib.monoschinos.models.detail

data class AnimeDetail(
    val title : String,
    val alternativeTitle : String?,
    val status : String,
    val coverImage : String,
    val profileImage : String,
    val release : String,
    val synopsis : String,
    val genres : List<String>
)