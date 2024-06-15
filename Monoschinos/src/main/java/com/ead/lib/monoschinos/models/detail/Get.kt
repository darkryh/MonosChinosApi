package com.ead.lib.monoschinos.models.detail

import android.content.Context
import com.ead.lib.monoschinos.core.Network

class Get(
    private val context : Context,
    private val seo : String,
) {

    suspend fun get() : AnimeDetail {
        return Network.animeDetail(context,seo)
    }

    suspend fun getNullable() : AnimeDetail? =
        try { get() } catch (e: Exception) { null }
}