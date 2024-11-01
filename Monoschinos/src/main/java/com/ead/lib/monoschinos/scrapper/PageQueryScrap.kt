package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Api
import com.ead.lib.monoschinos.core.system.extensions.getSrcAttr
import com.ead.lib.monoschinos.models.directory.Anime
import org.jsoup.Jsoup

/**
 * Anime page structure resolve the structure of the page
 * it works with singleton pattern
 */
val animePageQuery = Api.getAnimePageQueryStructure()

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
fun String.pageQuery() : List<Anime> {
    /**
     * Getting the anime page data with jsoup
     */
    val queryPage = Jsoup.parse(this)


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