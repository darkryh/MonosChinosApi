package com.ead.lib.monoschinos.models.directory

import com.ead.lib.monoschinos.core.Network

class Get(
    private val page: Int
) {

    suspend fun get() : List<Anime> {
        return Network.pageQuery(page)
    }
}