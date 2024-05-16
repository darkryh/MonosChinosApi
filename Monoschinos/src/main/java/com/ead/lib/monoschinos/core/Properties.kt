package com.ead.lib.monoschinos.core

object Properties {

    const val BASE_URL = "https://darkryh.github.io/MonosChinosApi/"
    const val HOME_API = "HomeStructure.json"
    const val ANIME_DETAIL_API = "AnimeDetailStructure.json"
    const val ANIME_PAGE_API = "AnimePageQuery.json"
    const val ANIME_SEARCH_API = "AnimeSearchQuery.json"
    const val PLAYER_API = "PlayerStructure.json"

    const val HOME_PAGE = "https://monoschinos2.com/"
    const val QUERY_PAGE = "animes?p="
    const val QUERY_SEARCH = "buscar?q="
    const val PLAY_PAGE = "ver/"
    const val ANIME_QUERY = "anime/"

    const val CAP_BLANK_MC2 = "https://monoschinos2.com/public/img/capblank.png"
    const val CAP_BLANK_ANIME_MC2 = "https://monoschinos2.com/public/img/anime.png"

    val seoChapterRegex = "/ver/([^/]+)".toRegex()
    val seoAnimeRegex = "/anime/([^/]+)".toRegex()
}