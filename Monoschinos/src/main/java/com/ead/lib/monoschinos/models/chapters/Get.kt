package com.ead.lib.monoschinos.models.chapters

import android.content.Context
import com.ead.lib.monoschinos.core.Network
import com.ead.lib.monoschinos.models.detail.Episode

class Get(
    private val context: Context,
    private val seo : String
) {

    suspend fun get() : List<Episode> {
        return Network.episodes(context,seo)
    }

    suspend fun getOrEmpty() : List<Episode> =
        try { get() } catch (e: Exception) { emptyList() }
}