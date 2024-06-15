package com.ead.lib.monoschinos.models.search

import android.content.Context
import com.ead.lib.monoschinos.core.Network
import com.ead.lib.monoschinos.models.directory.Anime

class Get(
    private val context : Context,
    private val query : String,
) {

    suspend fun get() : List<Anime> {
        return Network.searchQuery(context,query.replace(" ", "+"))
    }

    suspend fun getOrEmpty() : List<Anime> =
        try { get() } catch (e: Exception) { emptyList() }
}