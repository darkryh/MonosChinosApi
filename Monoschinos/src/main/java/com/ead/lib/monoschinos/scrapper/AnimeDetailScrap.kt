package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Api
import com.ead.lib.monoschinos.core.Properties.PAYLOAD_TOKEN
import com.ead.lib.monoschinos.core.connection.RestClient
import com.ead.lib.monoschinos.core.system.extensions.getSrcAttr
import com.ead.lib.monoschinos.models.detail.AnimeDetail
import okhttp3.FormBody
import okhttp3.Headers
import org.jsoup.Jsoup


private val headers = Headers.Builder()
    .add("accept", "application/json, text/javascript, */*; q=0.01")
    .add("accept-language", "es-419,es;q=0.8")
    .add("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
    .add("origin", RestClient.BASE_URL)
    .add("x-requested-with", "XMLHttpRequest")
    .build()

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
suspend fun String.animeDetailQuery(client: RestClient): AnimeDetail {
    /**
     * Getting the anime detail page data with jsoup
     */
    val document = Jsoup.parse(this)



    /**
     * Getting the title of the anime from the page
     */
    val title = document
        .select(animeDetail.title).text()



    /**
     * Getting the detail cover image from the page to
     * attribute to extract the image
     */
    val detailCoverAttr = srcDetailCover ?: (document.select(animeDetail.coverImage).getSrcAttr())
        .also { srcDetailCover = it }



    /**
     * Getting the detail profile image from the page to
     * attribute to extract the image
     */
    val detailProfileAttr = srcDetailProfile ?: (document.select(animeDetail.profileImage).getSrcAttr())
        .also { srcDetailProfile = it }

    val token = document.getCsrfToken()
    val internalApi = document.getInternalApi()

    val formBody = FormBody.Builder().add(PAYLOAD_TOKEN, token).build()

    val totalEpisodes= client.getPaginateEpisodeData(
        internalApi,
        formBody,
        headers
    ).first


    return AnimeDetail(
        /**
         * Mapping the whole jsoup data into the anime detail data
         */
        title = title,
        alternativeTitle = document
            .select(animeDetail.alternativeTitle).text().ifEmpty { null },
        status = document.select(animeDetail.status).firstOrNull()?.text().orEmpty(),
        coverImage = document.select(animeDetail.coverImage).attr(detailCoverAttr),
        profileImage = document.select(animeDetail.profileImage).attr(detailProfileAttr),
        totalEpisodes = totalEpisodes,
        release = document.select(animeDetail.release).text(),
        synopsis = document.select(animeDetail.synopsis).text(),
        genres = document.select(animeDetail.genres).map { element -> element.text() }
    )
}