package com.ead.lib.monoschinos.models.directory

import android.content.Context
import com.ead.lib.monoschinos.core.Network

class Get(
    private val context: Context,
    private val page: Int
) {

    suspend fun get() : List<Anime> {
        return Network.pageQuery(context,page)
    }

    suspend fun getOrEmpty() : List<Anime> =
        try { get() } catch (e: Exception) { emptyList() }
}