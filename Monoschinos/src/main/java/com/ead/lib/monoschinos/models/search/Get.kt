package com.ead.lib.monoschinos.models.search

import com.ead.lib.monoschinos.core.Network
import com.ead.lib.monoschinos.models.directory.Anime

class Get(
    private val query : String,
) {

    suspend fun get() : List<Anime> {
        return Network.searchQuery(query.replace(" ", "+"))
    }
}