package com.ead.lib.monoschinos.core.system.extensions

import android.content.Context
import com.ead.lib.monoschinos.R
import com.ead.lib.monoschinos.core.Properties
import com.ead.lib.monoschinos.models.exceptions.ProviderException
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun Document.suspend(context: Context): Document {
    return suspendCancellableCoroutine { continuation ->
        when (connection().response().statusCode()) {
            in 200..299 -> continuation.resume(this)
            400 -> continuation.resumeWithException(ProviderException(context.getString(R.string.bad_request)))
            401 -> continuation.resumeWithException(ProviderException(context.getString(R.string.unauthorized)))
            403 -> continuation.resumeWithException(ProviderException(context.getString(R.string.forbidden)))
            404 -> continuation.resumeWithException(ProviderException(context.getString(R.string.not_found)))
            500 -> continuation.resumeWithException(ProviderException(context.getString(R.string.internal_server_error)))
            502 -> continuation.resumeWithException(ProviderException(context.getString(R.string.bad_gateway)))
            503 -> continuation.resumeWithException(ProviderException(context.getString(R.string.service_unavailable)))
            504 -> continuation.resumeWithException(ProviderException(context.getString(R.string.gateway_timeout)))
            else -> continuation.resumeWithException(ProviderException(context.getString(R.string.unknown_error)))
        }
    }
}

fun Elements.getSrcAttr(): String {
    val result = attr("src")

    if (result == Properties.CAP_BLANK_MC2 || result == Properties.CAP_BLANK_ANIME_MC2  || result.isEmpty()) {
        return "data-src"
    }
    return "src"
}

fun Element.getSrcAttr(selectorQuery : String) : String {
    val result = select(selectorQuery).attr("src")

    if (result == Properties.CAP_BLANK_MC2 || result == Properties.CAP_BLANK_ANIME_MC2  || result.isEmpty()) {
        return "data-src"
    }
    return "src"
}