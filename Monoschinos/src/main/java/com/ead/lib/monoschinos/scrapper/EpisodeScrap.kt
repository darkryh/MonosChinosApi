package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Properties.CHAPTER_EPISODES
import com.ead.lib.monoschinos.core.Properties.CHAPTER_EPISODES_PAGINATE
import com.ead.lib.monoschinos.core.Properties.CHAPTER_EPISODES_PER_PAGE
import com.ead.lib.monoschinos.core.Properties.CSRF_TOKEN
import com.ead.lib.monoschinos.core.Properties.CSS_SELECTOR_INTERNAL_API
import com.ead.lib.monoschinos.core.Properties.PAYLOAD_PAGE
import com.ead.lib.monoschinos.core.Properties.PAYLOAD_TOKEN
import com.ead.lib.monoschinos.core.connection.RestClient
import com.ead.lib.monoschinos.core.system.extensions.toEpisodeList
import com.ead.lib.monoschinos.models.detail.Episode
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

    val token = document.select(CSRF_TOKEN).attr("content")
    val internalApi = document.select(CSS_SELECTOR_INTERNAL_API).attr("data-ajax")

    var formBody = FormBody.Builder().add(PAYLOAD_TOKEN, token).build()


    var responseBody = client.postRequest(
        internalApi,
        headers,
        formBody
    )

    val jsonObject = JSONObject(responseBody)

    val (total, perPage, paginateUrl) = Triple(
        jsonObject.getJSONArray(CHAPTER_EPISODES).length(),
        jsonObject.getInt(CHAPTER_EPISODES_PER_PAGE),
        jsonObject.getString(CHAPTER_EPISODES_PAGINATE)
    )

    val pages = (total / perPage) + 1

    val episodes = mutableListOf<Episode>()

    (1..pages).forEach { page ->

        formBody = FormBody.Builder()
            .add(PAYLOAD_TOKEN, token)
            .add(PAYLOAD_PAGE, "$page")
            .build()

        responseBody = client.postRequest(
            paginateUrl,
            headers,
            formBody
        )

        val arrayEpisodes = JSONObject(responseBody).getJSONArray("caps")

        episodes.addAll(arrayEpisodes.toEpisodeList())
    }

    return episodes
}
