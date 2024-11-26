package com.ead.lib.monoschinos

import com.ead.lib.monoschinos.core.connection.RestClient
import com.ead.lib.monoschinos.models.home.Home
import com.ead.lib.monoschinos.models.detail.AnimeDetail
import com.ead.lib.monoschinos.models.detail.Episode
import com.ead.lib.monoschinos.models.directory.Anime
import com.ead.lib.monoschinos.models.player.Player
import com.ead.lib.monoschinos.scrapper.episodes
import com.ead.lib.monoschinos.scrapper.extractAnimeDetail
import com.ead.lib.monoschinos.scrapper.extractHome
import com.ead.lib.monoschinos.scrapper.pageQuery
import com.ead.lib.monoschinos.scrapper.player
import com.ead.lib.monoschinos.scrapper.searchQuery

object MonosChinos {

    private val client = RestClient()

    suspend fun getHome() : Home {
        return client.request().extractHome()
    }

    suspend fun getAnime(seo : String) : AnimeDetail {
        return client.request("anime/$seo").extractAnimeDetail()
    }

    suspend fun pageQuery(query: Int) : List<Anime> {
        return client.request("animes?p=$query").pageQuery()
    }

    suspend fun searchQuery(query: String) : List<Anime> {
        return client.request("buscar?q=$query").searchQuery()
    }

    suspend fun episodes(seo: String) : List<Episode> {
        return client.request("anime/$seo").episodes(client)
    }

    suspend fun player(seo: String) : Player {
        return client.request("ver/$seo").player()
    }
}