package com.ead.lib.monoschinos.models.detail

import com.ead.lib.monoschinos.core.Properties

data class Episode(
    val title : String,
    val number : Int,
    val image : String,
    val url : String
) {
    val seo get() = Properties.seoChapterRegex.find(url)?.groupValues?.get(1) ?: "null"
}