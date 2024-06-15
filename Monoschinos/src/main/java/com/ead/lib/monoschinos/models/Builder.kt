package com.ead.lib.monoschinos.models

import android.content.Context


open class Builder(
    private val context: Context
) {

    /**
     * return the home page data from Monoschinos.
     *
     * example :
     *
     * val home = MonosChinos
     *
     *              .builder(context)
     *
     *              .homePage()
     *
     *              .get()
     *
     * home.lastChapters List type HomeChapter
     *
     * home.lastAnimes : List type HomeAnime
     */
    fun homePage(): com.ead.lib.monoschinos.models.home.Get {
        return com.ead.lib.monoschinos.models.home.Get(context)
    }



    /**
     * return the directory page data from Monoschinos.
     *
     * example :
     *
     * val page1 = MonosChinos
     *
     *              .builder(context)
     *
     *              .directoryPage(1)
     *
     *              .get()
     *
     * val page2 = MonosChinos
     *
     *              .builder(context)
     *
     *              .directoryPage(2)
     *
     *              .get()
     *
     * animeList : List type Anime
     */
    fun directoryPage(page: Int): com.ead.lib.monoschinos.models.directory.Get {
        return com.ead.lib.monoschinos.models.directory.Get(context,page)
    }



    /**
     * return the search page data from Monoschinos.
     *
     * example :
     *
     * val page1 = MonosChinos
     *
     *              .builder(context)
     *
     *              .searchPage("dragon ball")
     *
     *              .get()
     *
     * val page2 = MonosChinos
     *
     *              .builder(context)
     *
     *              .searchPage("death note")
     *
     *              .get()
     *
     * animeList : List type Anime
     */
    fun searchPage(query: String): com.ead.lib.monoschinos.models.search.Get {
        return com.ead.lib.monoschinos.models.search.Get(context,query)
    }



    /**
     * return the anime detail page data from Monoschinos.
     *
     * example :
     *
     * val anime : Anime?
     *
     * val exampleUrl = "https://monoschinos.com/anime/kimetsu-no-yaiba-hashira-geiko-hen-sub-espanol"
     *
     * val replaceSeo = "kimetsu-no-yaiba-hashira-geiko-hen-sub-espanol"
     *
     * val page1 = MonosChinos
     *
     *              .builder(context)
     *
     *              .animeDetailPage(anime.seo ?: replaceSeo)
     *
     *              .get()
     *
     * animeDetail : AnimeDetail
     */
    fun animeDetailPage(seo : String): com.ead.lib.monoschinos.models.detail.Get {
        return com.ead.lib.monoschinos.models.detail.Get(context,seo)
    }

    /**
     * return the chapters page data from Monoschinos.
     *
     * example:
     * val anime : Anime?
     *
     * val exampleUrl = "https://monoschinos.com/anime/kimetsu-no-yaiba-hashira-geiko-hen-sub-espanol"
     *
     * val replaceSeo = "kimetsu-no-yaiba-hashira-geiko-hen-sub-espanol"
     *
     * val episodes = MonosChinos
     *
     *                .builder(context)
     *
     *                .chaptersPage(anime.seo ?: replaceSeo)
     *
     *                .get()
     *
     * episodes : List type Episode
     */
    fun chaptersPage(seo : String): com.ead.lib.monoschinos.models.chapters.Get {
        return com.ead.lib.monoschinos.models.chapters.Get(context, seo)
    }



    /**
     * return the player page data from Monoschinos.
     *
     * example :
     *
     * val homeChapter : HomeChapter?
     *
     * val episode : Episode?
     *
     * val exampleUrl = "https://monoschinos2.com/ver/kenka-dokugaku-episodio-6"
     *
     * val replaceSeo = "kenka-dokugaku-episodio-6"
     *
     * val page1 = MonosChinos
     *
     *              .builder(context)
     *
     *              .playerPage(homeChapter.seo ?: episode.seo ?: replaceSeo)
     *
     *              .get()
     *
     * playerOptions : List type String
     *
     * playerDownloads : List type String
     */
    fun playerPage(seo : String): com.ead.lib.monoschinos.models.player.Get {
        return com.ead.lib.monoschinos.models.player.Get(context,seo)
    }
}