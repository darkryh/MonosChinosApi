package com.ead.lib.monoschinos.core

import com.ead.lib.monoschinos.core.Properties.ANIME_DETAIL_API
import com.ead.lib.monoschinos.core.Properties.ANIME_PAGE_API
import com.ead.lib.monoschinos.core.Properties.ANIME_SEARCH_API
import com.ead.lib.monoschinos.core.Properties.BASE_URL
import com.ead.lib.monoschinos.core.Properties.HOME_API
import com.ead.lib.monoschinos.core.Properties.PLAYER_API
import com.ead.lib.monoschinos.core.system.extensions.toAnimeDetailStructure
import com.ead.lib.monoschinos.core.system.extensions.toAnimeStructure
import com.ead.lib.monoschinos.core.system.extensions.toHomeStructure
import com.ead.lib.monoschinos.core.system.extensions.toPlayerStructure
import com.ead.lib.monoschinos.models.structure.detail.AnimeDetailStructure
import com.ead.lib.monoschinos.models.structure.directory.AnimeStructure
import com.ead.lib.monoschinos.models.structure.home.HomeStructure
import com.ead.lib.monoschinos.models.structure.player.PlayerStructure
import com.ead.lib.monoschinos.util.HttpUtil


object Api {

    private var mHomeStructure : HomeStructure? = null
    fun getHomeStructure() : HomeStructure = mHomeStructure ?: HttpUtil.getJson(BASE_URL + HOME_API).toHomeStructure()

    private var mAnimeDetailStructure : AnimeDetailStructure? = null
    fun getAnimeDetailStructure() : AnimeDetailStructure = mAnimeDetailStructure ?: HttpUtil.getJson(BASE_URL + ANIME_DETAIL_API).toAnimeDetailStructure()

    private var mAnimePageQueryStructure : AnimeStructure? = null
    fun getAnimePageQueryStructure(): AnimeStructure = mAnimePageQueryStructure ?: HttpUtil.getJson(BASE_URL + ANIME_PAGE_API).toAnimeStructure()

    private var mAnimeSearchQueryStructure : AnimeStructure? = null
    fun getAnimeSearchQueryStructure(): AnimeStructure = mAnimeSearchQueryStructure ?: HttpUtil.getJson(BASE_URL + ANIME_SEARCH_API).toAnimeStructure()

    private var mPlayerStructure : PlayerStructure? = null
    fun getPlayerStructure(): PlayerStructure = mPlayerStructure ?: HttpUtil.getJson(BASE_URL + PLAYER_API).toPlayerStructure()
}