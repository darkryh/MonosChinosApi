package com.ead.lib.monoschinos

import android.content.Context
import com.ead.lib.monoschinos.models.Builder

class MonosChinos {

    companion object {
        /**
         * return similar builder pattern
         */
        fun builder(context: Context) : Builder {
            return Builder(context)
        }
    }
}