package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Properties.PAYLOAD_PAGE
import com.ead.lib.monoschinos.core.Properties.PAYLOAD_TOKEN
import com.ead.lib.monoschinos.core.connection.RestClient
import com.ead.lib.monoschinos.core.system.extensions.toEpisodeList
import com.ead.lib.monoschinos.models.detail.Episode
import com.ead.lib.monoschinos.models.pagination.Pagination
import okhttp3.FormBody
import okhttp3.Headers
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


private val headers = Headers.Builder()
    .add("accept", "application/json, text/javascript, */*; q=0.01")
    .add("accept-language", "es-419,es;q=0.8")
    .add("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
    .add("origin", RestClient.BASE_URL)
    .add("x-requested-with", "XMLHttpRequest")
    .build()


suspend fun String.episodesQuery(client: RestClient): List<Episode> {

    /**
     * Getting the chapter page with okhttp and parsing with jsoup
     */
    val document: Document = Jsoup.parse(this)

    val token = document.getCsrfToken()
    val internalApi = document.getInternalApi()

    var formBody = FormBody.Builder().add(PAYLOAD_TOKEN, token).build()

    val (total, perPage, paginateUrl) = client.getPaginateEpisodeData(
        internalApi,
        formBody,
        headers
    )

    val pages = (total / perPage) + 1

    val episodes = mutableListOf<Episode>()

    (1..pages).forEach { page ->

        formBody = FormBody.Builder()
            .add(PAYLOAD_TOKEN, token)
            .add(PAYLOAD_PAGE, "$page")
            .build()

        val responseBody = client.postRequest(
            paginateUrl,
            headers,
            formBody
        )

        val arrayEpisodes = JSONObject(responseBody).getJSONArray("caps")

        episodes.addAll(arrayEpisodes.toEpisodeList())
    }

    return episodes
}

suspend fun String.episodesQuery(client: RestClient, page: Int? = null): Pagination<Episode> {

    val document: Document = Jsoup.parse(this)
    val token = document.getCsrfToken()
    val internalApi = document.getInternalApi()

    var formBody = FormBody.Builder().add(PAYLOAD_TOKEN, token).build()


    val (total, perPage, paginateUrl) = client.getPaginateEpisodeData(
        internalApi,
        formBody,
        headers
    )

    val totalPages = (total / perPage) + if (total % perPage != 0) 1 else 0

    val currentPage = page ?: 1
    val episodes = mutableListOf<Episode>()

    if (currentPage in 1..totalPages) {
        formBody = FormBody.Builder()
            .add(PAYLOAD_TOKEN, token)
            .add(PAYLOAD_PAGE, "$currentPage")
            .build()

        val responseBody = client.postRequest(
            paginateUrl,
            headers,
            formBody
        )

        val arrayEpisodes = JSONObject(responseBody).getJSONArray("caps")
        episodes.addAll(arrayEpisodes.toEpisodeList())
    }

    return Pagination(
        totalItems = total,
        itemsPerPage = perPage,
        currentPage = currentPage,
        totalPages = totalPages,
        items = episodes
    )
}