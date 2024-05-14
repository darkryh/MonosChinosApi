package com.ead.lib.monoschinos.models


open class Initializer {

    fun homePage(): com.ead.lib.monoschinos.models.home.Get {
        return com.ead.lib.monoschinos.models.home.Get()
    }

    fun directoryPage(page: Int): com.ead.lib.monoschinos.models.directory.Get {
        return com.ead.lib.monoschinos.models.directory.Get(page)
    }

    fun searchPage(query: String): com.ead.lib.monoschinos.models.search.Get {
        return com.ead.lib.monoschinos.models.search.Get(query)
    }

    fun animeDetailPage(seo : String): com.ead.lib.monoschinos.models.detail.Get {
        return com.ead.lib.monoschinos.models.detail.Get(seo)
    }

    fun playerPage(seo : String): com.ead.lib.monoschinos.models.player.Get {
        return com.ead.lib.monoschinos.models.player.Get(seo)
    }
}