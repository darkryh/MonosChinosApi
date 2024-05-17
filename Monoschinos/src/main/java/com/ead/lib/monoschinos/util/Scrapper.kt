package com.ead.lib.monoschinos.util

import android.content.Context
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
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

    suspend fun evaluate(url : String, code : String,regex: Regex) : String? {
        return suspendCancellableCoroutine { continuation ->
            Thread.onUi {

                scrapper?.apply {
                    webViewClient = object : WebViewClient() {

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