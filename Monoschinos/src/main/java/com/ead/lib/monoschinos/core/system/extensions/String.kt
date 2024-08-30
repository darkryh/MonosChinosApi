package com.ead.lib.monoschinos.core.system.extensions

fun String.cleanHtmlForJsoup(): String {
    return this.replace("\\u003C", "<").replace("\\n", "").replace("\\t", "").replace("\\", "")
}