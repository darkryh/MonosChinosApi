package com.ead.lib.monoschinos.models.player

import com.ead.lib.monoschinos.core.Network

class Get(
    private val seo : String,
) {

    suspend fun get() : Player? {
        return Network.player(seo)
    }
}