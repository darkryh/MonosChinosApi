package com.ead.lib.monoschinos.core.cookiejar

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

object MonosChinosJar : CookieJar {
    private val cookieStore = HashMap<String, List<Cookie>>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url.host] ?: emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies
    }
}