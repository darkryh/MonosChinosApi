package com.ead.lib.monoschinos.core

object Properties {

    const val BASE_URL = "https://darkryh.github.io/MonosChinosApi/"
    const val HOME_API = "HomeStructure.json"
    const val ANIME_DETAIL_API = "AnimeDetailStructure.json"
    const val ANIME_PAGE_API = "AnimePageQuery.json"
    const val ANIME_SEARCH_API = "AnimeSearchQuery.json"
    const val PLAYER_API = "PlayerStructure.json"

    const val CAP_BLANK_MC2 = "https://monoschinos2.com/public/img/capblank.png"
    const val CAP_BLANK_ANIME_MC2 = "https://monoschinos2.com/public/img/anime.png"

    val seoChapterRegex = "/ver/([^/]+)".toRegex()
    val seoAnimeRegex = "/anime/([^/]+)".toRegex()

    const val CSRF_TOKEN = "meta[name='csrf-token']"
    const val CSS_SELECTOR_INTERNAL_API = ".caplist"

    const val CHAPTER_EPISODES = "eps"
    const val CHAPTER_EPISODES_PER_PAGE = "perpage"
    const val CHAPTER_EPISODES_PAGINATE = "paginate_url"


    const val PAYLOAD_TOKEN = "_token"
    const val PAYLOAD_PAGE = "p"

}