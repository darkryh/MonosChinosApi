package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Api
import com.ead.lib.monoschinos.core.system.extensions.getSrcAttr
import com.ead.lib.monoschinos.models.home.Home
import com.ead.lib.monoschinos.models.home.HomeAnime
import com.ead.lib.monoschinos.models.home.HomeChapter
import org.jsoup.Jsoup


private val homeStructure = Api.getHomeStructure()

/**
 * validation method to get src attribute
 */
private var srcHomeChapter : String? = null

/**
 * validation method to get src attribute
 */
private var srcHomeAnime : String? = null



fun String.extractHome() : Home {
    /**
     * Getting the home page data with jsoup
     */
    val homePage = Jsoup.parse(this)


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