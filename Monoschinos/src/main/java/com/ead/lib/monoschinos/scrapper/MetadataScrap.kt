package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Properties.CHAPTER_EPISODES
import com.ead.lib.monoschinos.core.Properties.CHAPTER_EPISODES_PAGINATE
import com.ead.lib.monoschinos.core.Properties.CHAPTER_EPISODES_PER_PAGE
import com.ead.lib.monoschinos.core.Properties.CSRF_TOKEN
import com.ead.lib.monoschinos.core.Properties.CSS_SELECTOR_INTERNAL_API
import com.ead.lib.monoschinos.core.connection.RestClient
import okhttp3.FormBody
import okhttp3.Headers
import org.json.JSONObject
import org.jsoup.nodes.Document


suspend fun RestClient.getPaginateEpisodeData(
    internalApi: String,
    body: FormBody,
    headers: Headers
): Triple<Int, Int, String> {

    val responseBody = postRequest(
        internalApi,
        headers,
        body
    )

    JSONObject(responseBody).let { jsonObject ->
        return Triple(
            jsonObject.getJSONArray(CHAPTER_EPISODES).length(),
            jsonObject.getInt(CHAPTER_EPISODES_PER_PAGE),
            jsonObject.getString(CHAPTER_EPISODES_PAGINATE)
        )
    }
}

fun Document.getCsrfToken(): String {
    return select(CSRF_TOKEN).attr("content")
}

fun Document.getInternalApi(): String {
    return select(CSS_SELECTOR_INTERNAL_API).attr("data-ajax")
}