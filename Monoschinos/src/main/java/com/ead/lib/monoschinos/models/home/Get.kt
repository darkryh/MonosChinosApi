package com.ead.lib.monoschinos.models.home

import com.ead.lib.monoschinos.core.Network
import com.ead.lib.monoschinos.models.Home

open class Get(
) {

    suspend fun get() : Home? {
        return Network.home()
    }
}