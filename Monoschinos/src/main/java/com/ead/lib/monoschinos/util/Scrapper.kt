package com.ead.lib.monoschinos.util

import android.content.Context
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.ead.lib.cloudflare_bypass.ByPassClient
import com.ead.lib.monoschinos.models.scrapper.ScrapperWebView
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object Scrapper {

    private var scrapper : ScrapperWebView? = null


    suspend fun initialize(context: Context) {
        if (scrapper != null) return

        Thread.onUi {
            scrapper = ScrapperWebView(context)
        }

        delay(200)
    }

    suspend fun getHeaders(mUrl: String) : HashMap<String,String> {
        return suspendCancellableCoroutine { continuation ->
            Thread.onUi {

                scrapper?.apply {
                    webViewClient = object : ByPassClient() {

                        private var hashMapHeaders = hashMapOf<String,String>()

                        private var isPassed = false
                        private val cookieManager = CookieManager.getInstance()
                        private var cookiesString : String? = null

                        override fun onPageFinishedByPassed(view: WebView?, url: String?) {
                            cookiesString = cookieManager.getCookie(mUrl)

                            if (isPassed || cookiesString == null || view?.title.isNullOrEmpty()) return
                            isPassed = true

                            val cookies =
                                cookiesString
                                    ?.split(";")
                                    ?.map { it.trim().split("=") }
                                    ?.associate {
                                        it[0] to it.getOrElse(1) { "" }
                                    } as HashMap<String, String>

                            continuation.resume((cookies + hashMapHeaders) as HashMap<String, String>)
                        }

                        override fun shouldInterceptRequest(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): WebResourceResponse? {
                            if (request?.url.toString() == mUrl) {
                                hashMapHeaders = request?.requestHeaders as HashMap<String, String>
                            }
                            return super.shouldInterceptRequest(view, request)
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            if (error?.errorCode != -1 || error.errorCode != -2) return

                            continuation.cancel(Throwable("error code = ${error.errorCode}, error description =${error.description}"))
                        }
                    }
                    loadUrl(mUrl)
                }
            }

            continuation.invokeOnCancellation {
                scrapper?.destroy()
                scrapper = null
            }
        }
    }

    suspend fun evaluate(url : String, code : String,regex: Regex) : String? {
        return suspendCancellableCoroutine { continuation ->
            Thread.onUi {

                scrapper?.apply {
                    webViewClient = object : ByPassClient() {

                        override fun shouldInterceptRequest(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): WebResourceResponse? {
                            if (regex.matches(request?.url.toString())) {
                                Thread.onUi {
                                    view?.evaluateJavascript(code) {
                                        continuation.resume(it)
                                    }
                                }
                            }
                            return super.shouldInterceptRequest(view, request)
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            if (error?.errorCode != -1 || error.errorCode != -2) return

                            continuation.cancel(Throwable("error code = ${error.errorCode}, error description =${error.description}"))
                        }
                    }
                }

                scrapper?.loadUrl(url)
            }

            continuation.invokeOnCancellation {
                scrapper?.destroy()
                scrapper = null
            }
        }
    }
}