package com.ead.lib.monoschinos

import com.ead.lib.monoschinos.models.Builder

class MonosChinos {

    companion object {
        /**
         * return similar builder pattern
         */
        fun builder() : Builder {
            return Builder()
        }
    }
}