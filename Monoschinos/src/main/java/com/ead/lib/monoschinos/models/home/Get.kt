package com.ead.lib.monoschinos.models.home

import android.content.Context
import com.ead.lib.monoschinos.core.Network
import com.ead.lib.monoschinos.models.Home

open class Get(
    private val context: Context
) {

    suspend fun get() : Home {
        return Network.home(context)
    }

    suspend fun getNullable() : Home? =
        try { get() } catch (e: Exception) { null }
}