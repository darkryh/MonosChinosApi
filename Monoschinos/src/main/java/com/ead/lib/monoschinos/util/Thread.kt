package com.ead.lib.monoschinos.util

import android.os.Handler
import android.os.Looper

object Thread {

    private val handler = Handler(Looper.getMainLooper())

    fun onUi(action: () -> Unit) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(action)
        } else {
            action.invoke()
        }
    }
}