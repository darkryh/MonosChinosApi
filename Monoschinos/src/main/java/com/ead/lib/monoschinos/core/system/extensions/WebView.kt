package com.ead.lib.monoschinos.core.system.extensions

import android.webkit.ValueCallback
import android.webkit.WebView

fun WebView.getSourceCode(resultCallback: ValueCallback<String>?) {
    evaluateJavascript("document.documentElement.outerHTML",resultCallback)
}