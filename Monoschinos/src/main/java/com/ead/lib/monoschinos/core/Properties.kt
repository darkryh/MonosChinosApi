package com.ead.lib.monoschinos.core

object Properties {

    const val HOME_PAGE = "https://monoschinos2.com/"
    const val QUERY_PAGE = "animes?p="
    const val QUERY_SEARCH = "buscar?q="
    const val PLAY_PAGE = "ver/"
    const val ANIME_QUERY = "anime/"

    val seoChapterRegex = "/ver/([^/]+)".toRegex()
    val seoAnimeRegex = "/anime/([^/]+)".toRegex()
}