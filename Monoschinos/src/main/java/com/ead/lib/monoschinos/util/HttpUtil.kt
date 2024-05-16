package com.ead.lib.monoschinos.util

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object HttpUtil {

    private const val GET = "GET"

    fun getJson(mUrl : String) : JSONObject {
        val url = URL(mUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = GET

        connection.connect()

        val inputStream = connection.inputStream

        return JSONObject(
            inputStream.bufferedReader().use { it.readText() }
        ).also { connection.disconnect() }
    }
}