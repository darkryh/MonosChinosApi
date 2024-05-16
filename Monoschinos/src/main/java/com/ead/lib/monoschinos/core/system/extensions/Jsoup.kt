package com.ead.lib.monoschinos.core.system.extensions

import com.ead.lib.monoschinos.core.Properties
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import kotlin.coroutines.resume

suspend fun Document.suspend() : Document? {
    return suspendCancellableCoroutine { continuation ->
        when (connection().response().statusCode()) {
            in 400..599 -> continuation.resume(null)
            in 200..299 -> continuation.resume(this)
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