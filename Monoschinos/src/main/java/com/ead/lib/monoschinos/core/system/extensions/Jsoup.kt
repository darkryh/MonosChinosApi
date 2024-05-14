package com.ead.lib.monoschinos.core.system.extensions

import kotlinx.coroutines.suspendCancellableCoroutine
import org.jsoup.nodes.Document
import kotlin.coroutines.resume

suspend fun Document.suspend() : Document? {
    return suspendCancellableCoroutine { continuation ->
        when (connection().response().statusCode()) {
            in 400..599 -> continuation.resume(null)
            in 200..299 -> continuation.resume(this)
        }
    }
}