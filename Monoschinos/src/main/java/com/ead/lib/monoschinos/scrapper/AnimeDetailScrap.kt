package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Api
import com.ead.lib.monoschinos.core.system.extensions.getSrcAttr
import com.ead.lib.monoschinos.models.detail.AnimeDetail
import org.jsoup.Jsoup


/**
 * Anime detail structure resolve the structure of the page
 * it works with singleton pattern
 */
val animeDetail = Api.getAnimeDetailStructure()


/**
 * validation method to get src attribute
 */
private var srcDetailCover : String? = null

/**
 * validation method to get src attribute
 */
private var srcDetailProfile : String? = null



/**
 * This is anime detail method to get the anime detail data
 * from the web page MonosChinos it's compose with
 * Jsoup to extract the data from the page and a api
 * call to get the structure queries updated of the page
 */
fun String.extractAnimeDetail() : AnimeDetail {
    /**
     * Getting the anime detail page data with jsoup
     */
    val detailPage = Jsoup.parse(this)



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