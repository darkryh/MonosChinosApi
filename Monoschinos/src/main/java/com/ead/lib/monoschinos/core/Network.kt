package com.ead.lib.monoschinos.core

import android.content.Context
import com.ead.lib.monoschinos.core.system.extensions.getSrcAttr
import com.ead.lib.monoschinos.core.system.extensions.suspend
import com.ead.lib.monoschinos.core.system.extensions.toEpisodeList
import com.ead.lib.monoschinos.models.Home
import com.ead.lib.monoschinos.models.detail.AnimeDetail
import com.ead.lib.monoschinos.models.detail.Episode
import com.ead.lib.monoschinos.models.directory.Anime
import com.ead.lib.monoschinos.models.home.HomeAnime
import com.ead.lib.monoschinos.models.home.HomeChapter
import com.ead.lib.monoschinos.models.player.Player
import com.ead.lib.monoschinos.models.structure.detail.AnimeDetailStructure
import com.ead.lib.monoschinos.models.structure.directory.AnimeStructure
import com.ead.lib.monoschinos.models.structure.home.HomeStructure
import com.ead.lib.monoschinos.models.structure.player.PlayerStructure
import com.ead.lib.monoschinos.util.Scrapper
import org.json.JSONArray
import org.jsoup.Jsoup

object Network {

    /**
     * Home page structure resolve the structure of update of the page
     * it works with singleton pattern
     */
    private var mHomeStructure : HomeStructure? = null


    /**
     * validation method to get src attribute
     */
    private var srcHomeChapter : String? = null

    /**
     * validation method to get src attribute
     */
    private var srcHomeAnime : String? = null


    /**
     * This es home method to get the home page data
     * from the web page MonosChinos it's compose with
     * Jsoup to extract the data from the page
     * and a call to an api to get the structure queries updated of the page
     */
    suspend fun home(context: Context) : Home {

        /**
         * Assigning variable with the singleton pattern
         */
        val homeStructure = mHomeStructure ?: Api.getHomeStructure().also { mHomeStructure = it }



        /**
         * Getting the home page data with jsoup
         */
        val homePage = Jsoup
            .connect(Properties.HOME_PAGE)
            .get()
            .suspend(context)



        /**
         * Getting the last chapters structure from the previous variable home structure
         * and saving the class list container for the las chapters
         */
        val lastChaptersClassList = homePage.select(homeStructure.homeChapterStructure.classList)



        /**
         * Get the available attribute for the last chapters
         * to extract the image
         */
        val homeChapterAttr = srcHomeChapter ?: (lastChaptersClassList.firstOrNull()?.
        getSrcAttr(homeStructure.homeChapterStructure.image) ?: "unknown")
            .also { srcHomeChapter = it }



        /**
         * Getting the anime homes structure from the previous variable home structure
         * and saving the class list container for the anime homes
         */
        val homeAnimeClassList = homePage.select(homeStructure.homeAnimeStructure.classList)



        /**
         * Get the available attribute for the anime homes
         * to extract the image
         */
        val homeAnimeAttr = srcHomeAnime ?: (homeAnimeClassList.firstOrNull()?.
        getSrcAttr(homeStructure.homeAnimeStructure.image) ?: "unknown")
            .also { srcHomeAnime = it }



        return Home(
            /**
             * Mapping the whole jsoup data into the home data
             */
            lastChapters = lastChaptersClassList.map { element ->
                HomeChapter(
                    title = element.select(homeStructure.homeChapterStructure.title).text(),
                    number = element.select(homeStructure.homeChapterStructure.number).text().toIntOrNull()?:-1,
                    type = element.select(homeStructure.homeChapterStructure.type).text(),
                    image = element.select(homeStructure.homeChapterStructure.image).attr(homeChapterAttr),
                    url = element.select(homeStructure.homeChapterStructure.url).attr("href")
                )
            },
            recentSeries = homeAnimeClassList.map { element ->
                HomeAnime(
                    title = element.select(homeStructure.homeAnimeStructure.title).text(),
                    type = element.select(homeStructure.homeAnimeStructure.type).text(),
                    image = element.select(homeStructure.homeAnimeStructure.image).attr(homeAnimeAttr),
                    url = element.select(homeStructure.homeAnimeStructure.url).attr("href")
                )
            }
        )
    }



    /**
     * Anime detail structure resolve the structure of the page
     * it works with singleton pattern
     */
    private var mAnimeDetailStructure : AnimeDetailStructure?=null


    /**
     * validation method to get src attribute
     */
    private var srcDetailCover : String? = null

    /**
     * validation method to get src attribute
     */
    private var srcDetailProfile : String? = null



    /**
     * This es anime detail method to get the anime detail data
     * from the web page MonosChinos it's compose with
     * Jsoup to extract the data from the page and a api
     * call to get the structure queries updated of the page
     */
    suspend fun animeDetail(context: Context,seo : String) : AnimeDetail {

        /**
         * Assigning variable with the singleton pattern
         */
        val animeDetail = mAnimeDetailStructure ?: Api.getAnimeDetailStructure().also { mAnimeDetailStructure = it }



        /**
         * Getting the anime detail page data with jsoup
         */
        val detailPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.ANIME_QUERY + seo)
            .get()
            .suspend(context)



        /**
         * Getting the title of the anime from the page
         */
        val title = detailPage
            .select(animeDetail.title).text()



        /**
         * Getting the detail cover image from the page to
         * attribute to extract the image
         */
        val detailCoverAttr = srcDetailCover ?: (detailPage.select(animeDetail.coverImage).getSrcAttr())
            .also { srcDetailCover = it }



        /**
         * Getting the detail profile image from the page to
         * attribute to extract the image
         */
        val detailProfileAttr = srcDetailProfile ?: (detailPage.select(animeDetail.profileImage).getSrcAttr())
            .also { srcDetailProfile = it }



        return AnimeDetail(
            /**
             * Mapping the whole jsoup data into the anime detail data
             */
            title = title,
            alternativeTitle = detailPage
                .select(animeDetail.alternativeTitle).text().ifEmpty { null },
            status = detailPage.select(animeDetail.status).firstOrNull()?.text().orEmpty(),
            coverImage = detailPage.select(animeDetail.coverImage).attr(detailCoverAttr),
            profileImage = detailPage.select(animeDetail.profileImage).attr(detailProfileAttr),
            release = detailPage.select(animeDetail.release).text(),
            synopsis = detailPage.select(animeDetail.synopsis).text(),
            genres = detailPage.select(animeDetail.genres).map { element -> element.text() }
        )
    }



    /**
     * Anime page structure resolve the structure of the page
     * it works with singleton pattern
     */
    private var mAnimePageQueryStructure : AnimeStructure?=null

    /**
     * validation method to get src attribute
     */
    private var srcPageQuery : String? = null



    /**
     * This es anime page query method to get the anime page data
     * from the web page MonosChinos it's compose with
     * Jsoup to extract the data from the page and a api call
     * to get the structure queries updated of the page
     */
    suspend fun pageQuery(context: Context,query: Int) : List<Anime> {

        /**
         * Assigning variable with the singleton pattern
         */
        val animePageQuery = mAnimePageQueryStructure ?: Api.getAnimePageQueryStructure().also { mAnimePageQueryStructure = it }



        /**
         * Getting the anime page data with jsoup
         */
        val queryPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.QUERY_PAGE + query)
            .get()
            .suspend(context)



        /**
         * Getting the animes class list from the page and
         * saving the class list container for the animes
         */
        val animesClassList = queryPage.select(animePageQuery.classList)



        /**
         * Getting the page query attribute to extract the image
         */
        val pageQueryAttr = srcPageQuery ?: (animesClassList.firstOrNull()?.
        getSrcAttr(animePageQuery.image) ?: return emptyList())
            .also { srcPageQuery = it }



        return animesClassList.map { element ->
            /**
             * Mapping the whole jsoup data into the anime data
             */
            Anime(
                title = element.select(animePageQuery.title).text(),
                type = element.select(animePageQuery.type).text().split("·").firstOrNull() ?: "unknown",
                year = element.select(animePageQuery.year).text().split("·").getOrNull(1)?.trim()?.toIntOrNull() ?: -1,
                image = element.select(animePageQuery.image).attr(pageQueryAttr),
                url = element.select(animePageQuery.url).attr("href")
            )
        }
    }



    /**
     * Anime search query structure resolve the structure of the page
     * it works with singleton pattern
     */
    private var mAnimeSearchQueryStructure : AnimeStructure?=null

    private var srcSearchQuery : String? = null


    /**
     * This es anime search query method to get the anime search data
     * from the web page MonosChinos it's compose with
     * Jsoup to extract the data from the page and a api call
     * to get the structure queries updated of the page
     */
    suspend fun searchQuery(context: Context,query: String) : List<Anime> {

        /**
         * Assigning variable with the singleton pattern
         */
        val animeSearchQuery = mAnimeSearchQueryStructure ?: Api.getAnimeSearchQueryStructure().also { mAnimeSearchQueryStructure = it }



        /**
         * Getting the anime search data with jsoup
         */
        val queryPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.QUERY_SEARCH + query)
            .get()
            .suspend(context)



        /**
         * Getting the animes class list from the page and
         * saving the class list container for the animes
         */
        val animesClassList = queryPage.select(animeSearchQuery.classList)



        /**
         * Getting the search query attribute to extract the image
         */
        val searchQueryAttr = srcSearchQuery ?: (animesClassList.firstOrNull()?.
        getSrcAttr(animeSearchQuery.image) ?: return emptyList())
            .also { srcSearchQuery = it }



        return animesClassList.map { element ->
            /**
             * Mapping the whole jsoup data into the anime data
             */
            Anime(
                title = element.select(animeSearchQuery.title).text(),
                type = element.select(animeSearchQuery.type).text(),
                year = element.select(animeSearchQuery.year).text().toIntOrNull() ?: -1,
                image = element.select(animeSearchQuery.image).attr(searchQueryAttr),
                url = element.select(animeSearchQuery.url).attr("href")
            )
        }
    }


    /**
     * Initialization of the Scrapper
     * getting evaluation code
     */
    suspend fun episodes(context: Context,seo : String) : List<Episode> {
        /**
         * Initialize the Scrapper
         */
        Scrapper.initialize(context)

        /**
         * Evaluating the chapter page
         * and getting the data
         */
        val  data = Scrapper.evaluate(Properties.HOME_PAGE + Properties.ANIME_QUERY + seo, code = chapterRequester, regex = regexRequested)

        /**
         * Mapping the json into episodes
         */
        return JSONArray(data).toEpisodeList()
    }



    /**
     * Player page structure resolve the structure of the page
     * it works with singleton pattern
     */
    private var mPlayerStructure : PlayerStructure?=null



    /**
     * This es player method to get the player data
     * from the web page MonosChinos it's compose with
     * Jsoup to extract the data from the page and a api call
     * to get the structure queries updated of the page
     *
     * Including a decoder to handle the encrypted data in player
     */
    suspend fun player(context: Context,seo : String) : Player {

        /**
         * Assigning variable with the singleton pattern
         */
        val player = mPlayerStructure ?: Api.getPlayerStructure().also { mPlayerStructure = it }



        /**
         * Getting the player page data with jsoup
         */
        val playerPage = Jsoup
            .connect(Properties.HOME_PAGE + Properties.PLAY_PAGE + seo)
            .get()
            .suspend(context)



        return Player(
            /**
             * Mapping the whole jsoup data into the player data
             */
            options = playerPage.select(player.optionsClassList).map { element ->
                decoder(element.attr(player.optionAttribute))
            },
            downloads = playerPage.select(player.downloadsClassList).map { element ->
                element.attr("href")
            }
        )
    }

}