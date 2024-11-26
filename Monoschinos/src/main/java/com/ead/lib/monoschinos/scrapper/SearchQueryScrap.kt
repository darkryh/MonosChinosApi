package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Api
import com.ead.lib.monoschinos.core.system.extensions.getSrcAttr
import com.ead.lib.monoschinos.models.directory.Anime
import org.jsoup.Jsoup

/**
 * Anime search query structure resolve the structure of the page
 * it works with singleton pattern
 */
private val animeSearchQuery = Api.getAnimeSearchQueryStructure()

private var srcSearchQuery : String? = null


/**
 * This es anime search query method to get the anime search data
 * from the web page MonosChinos it's compose with
 * Jsoup to extract the data from the page and a api call
 * to get the structure queries updated of the page
 */
fun String.searchQuery() : List<Anime> {
    /**
     * Getting the anime search data with jsoup
     */
    val queryPage = Jsoup.parse(this)


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