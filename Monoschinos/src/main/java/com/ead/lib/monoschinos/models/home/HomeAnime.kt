package com.ead.lib.monoschinos.models.home

import com.ead.lib.monoschinos.core.Properties

data class HomeAnime(
    val title : String,
    val type : String,
    val image : String,
    val url : String
) {
    val seo get() = Properties.seoAnimeRegex.find(url)?.groupValues?.get(1) ?: "null"
}