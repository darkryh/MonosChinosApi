package com.ead.lib.monoschinos.core.client

import com.ead.lib.monoschinos.core.connection.RestClient.Companion.BASE_URL
import com.ead.lib.monoschinos.core.cookiejar.MonosChinosJar
import okhttp3.OkHttpClient
import okhttp3.Request

open class MonosChinosClient {
    protected open val httpClient = OkHttpClient.Builder()
        .cookieJar(MonosChinosJar)
        .followRedirects(true)
        .followSslRedirects(true)
        .build()

    protected open val requestHttpClient = Request.Builder()
        .url(BASE_URL)
        .build()
}