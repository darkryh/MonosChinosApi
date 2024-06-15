package com.ead.lib.monoschinos.models.player

import android.content.Context
import com.ead.lib.monoschinos.core.Network

class Get(
    private val context: Context,
    private val seo : String,
) {

    suspend fun get() : Player {
        return Network.player(context,seo)
    }

    suspend fun getNullable() : Player? = try { get() }
    catch (e: Exception) { null }
}