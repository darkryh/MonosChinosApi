package com.ead.lib.monoschinos.models.detail

import com.ead.lib.monoschinos.core.Network

class Get(
    private val seo : String,
) {

    suspend fun get() : AnimeDetail? {
        return Network.animeDetail(seo)
    }
}