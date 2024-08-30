package com.ead.lib.monoschinos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import com.ead.lib.cloudflare_bypass.ByPassClient
import com.ead.lib.monoschinos.util.Thread
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CloudFlareInterceptor(
) : Interceptor {

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun intercept(chain: Interceptor.Chain): Response {

        Log.d("test", "intercept: ")

        val originalRequest = chain.request()

        val originalResponse = chain.proceed(chain.request())

        if (!(originalResponse.code in ERROR_CODES && originalResponse.header("Server") in SERVER_CHECK)) {
            Log.d("test", "intercept: not")
            return originalResponse
        }

        Log.d("test", "intercept: trying")
        return try {
            originalResponse.close()
            //val request = resolveWithWebView(originalRequest, client)

            chain.proceed(originalRequest)
        } catch (e: Exception) {
            // Because OkHttp's enqueue only handles IOExceptions, wrap the exception so that
            // we don't crash the entire app
            throw IOException(e)
        }
    }

    /*@SuppressLint("SetJavaScriptEnabled")
    fun resolveWithWebView(request: Request, client: OkHttpClient): Request {
        // We need to lock this thread until the WebView finds the challenge solution url, because
        // OkHttp doesn't support asynchronous interceptors.
        val latch = CountDownLatch(1)

        var webView: WebView? = null

        val origRequestUrl = request.url.toString()
        val headers = request.headers.toMultimap().mapValues { it.value.getOrNull(0) ?: "" }.toMutableMap()

        handler.post {
            val webview = WebView(context)
            webView = webview
            with(webview.settings) {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = false
                userAgentString = request.header("User-Agent")
                    ?: "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
            }

            webview.webViewClient = object : ByPassClient() {

                private var isPassed = false
                private val cookieManager = CookieManager.getInstance()
                private var cookiesString : String? = null

                override fun onPageFinishedByPassed(view: WebView?, url: String?) {
                    cookiesString = cookieManager.getCookie(origRequestUrl)

                    if (isPassed || cookiesString == null || view?.title.isNullOrEmpty()) return
                    isPassed = true

                    latch.countDown()
                    Log.d("test", "onPageFinishedByPassed: ")
                }
            }

            webview.loadUrl(origRequestUrl, headers)
        }

        // Wait a reasonable amount of time to retrieve the solution. The minimum should be
        // around 4 seconds but it can take more due to slow networks or server issues.
        latch.await(30, TimeUnit.SECONDS)

        Log.d("test", "resolveWithWebView: passed")

        handler.post {
            webView?.stopLoading()
            webView?.destroy()
            webView = null
        }

        val cookies = CookieManager.getInstance()
            ?.getCookie(origRequestUrl)
            ?.split(";")
            ?.mapNotNull { Cookie.parse(request.url, it) }
            ?: emptyList<Cookie>()

        // Copy web view cookies to OkHTTP cookie storage
        cookies.forEach {
            client.cookieJar.saveFromResponse(
                url = HttpUrl.Builder()
                    .scheme("http")
                    .host(it.domain)
                    .build(),
                cookies = cookies,
            )
        }

        return createRequestWithCookies(request, cookies)
    }

    private fun createRequestWithCookies(request: Request, cookies: List<Cookie>): Request {
        val convertedForThisRequest = cookies.filter {
            it.matches(request.url)
        }
        val existingCookies = Cookie.parseAll(
            request.url,
            request.headers,
        )

        val filteredExisting = existingCookies.filter { existing ->
            convertedForThisRequest.none { converted -> converted.name == existing.name }
        }

        val newCookies = filteredExisting + convertedForThisRequest

        return request.newBuilder()
            .header("Cookie", newCookies.joinToString("; ") { "${it.name}=${it.value}" })
            .build()
    }*/

    companion object {
        val ERROR_CODES = listOf(403, 503)
        val SERVER_CHECK = arrayOf("cloudflare-nginx", "cloudflare")
    }
}