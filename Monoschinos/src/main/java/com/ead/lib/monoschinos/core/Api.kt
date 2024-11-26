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

    private var homeStructure : HomeStructure? = null
    fun getHomeStructure() : HomeStructure = homeStructure ?: HttpUtil.getJson(BASE_URL + HOME_API).toHomeStructure().also { homeStructure = it }

    private var animeDetailStructure : AnimeDetailStructure? = null
    fun getAnimeDetailStructure() : AnimeDetailStructure = animeDetailStructure ?: HttpUtil.getJson(BASE_URL + ANIME_DETAIL_API).toAnimeDetailStructure().also { animeDetailStructure = it }

    private var animePageQueryStructure : AnimeStructure? = null
    fun getAnimePageQueryStructure(): AnimeStructure = animePageQueryStructure ?: HttpUtil.getJson(BASE_URL + ANIME_PAGE_API).toAnimeStructure().also { animePageQueryStructure = it }

    private var animeSearchQueryStructure : AnimeStructure? = null
    fun getAnimeSearchQueryStructure(): AnimeStructure = animeSearchQueryStructure ?: HttpUtil.getJson(BASE_URL + ANIME_SEARCH_API).toAnimeStructure().also { animeSearchQueryStructure = it }

    private var playerStructure : PlayerStructure? = null
    fun getPlayerStructure(): PlayerStructure = playerStructure ?: HttpUtil.getJson(BASE_URL + PLAYER_API).toPlayerStructure().also { playerStructure = it }
}