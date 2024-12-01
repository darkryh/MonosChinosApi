@file:Suppress("unused")

package com.ead.lib.monoschinos

import com.ead.lib.monoschinos.core.connection.RestClient
import com.ead.lib.monoschinos.models.home.Home
import com.ead.lib.monoschinos.models.detail.AnimeDetail
import com.ead.lib.monoschinos.models.detail.Episode
import com.ead.lib.monoschinos.models.directory.Anime
import com.ead.lib.monoschinos.models.pagination.Pagination
import com.ead.lib.monoschinos.models.player.Player
import com.ead.lib.monoschinos.scrapper.episodesQuery
import com.ead.lib.monoschinos.scrapper.animeDetailQuery
import com.ead.lib.monoschinos.scrapper.homeQuery
import com.ead.lib.monoschinos.scrapper.pageQuery
import com.ead.lib.monoschinos.scrapper.playerQuery
import com.ead.lib.monoschinos.scrapper.searchQuery

object MonosChinos {

    private val client = RestClient()

    /**
     * Fetches the homepage and extracts relevant information, such as the latest episodes and recent series.
     *
     * @return A [Home] object containing the retrieved information.
     */
    suspend fun getHome(): Home {
        return client.request().homeQuery()
    }

    /**
     * Retrieves details of a specific anime using its SEO identifier.
     *
     * @param seo The SEO identifier of the anime.
     * @return An [AnimeDetail] object with the anime's details.
     */
    suspend fun getAnime(seo: String): AnimeDetail {
        return client.request("anime/$seo").animeDetailQuery(client)
    }

    /**
     * Retrieves a list of anime from a specific page.
     *
     * @param query The page number to query.
     * @return A list of [Anime] objects corresponding to the requested page.
     */
    suspend fun getPageQuery(query: Int): List<Anime> {
        return client.request("animes?p=$query").pageQuery()
    }

    /**
     * Searches for anime that match a provided search string.
     *
     * @param query The search term.
     * @return A list of [Anime] objects that match the search criteria.
     */
    suspend fun getSearchQuery(query: String): List<Anime> {
        return client.request("buscar?q=$query").searchQuery()
    }

    /**
     * Retrieves the list of episodes for a specific anime using its SEO identifier.
     *
     * @param seo The SEO identifier of the anime.
     * @return A list of [Episode] objects corresponding to the anime.
     */
    suspend fun getEpisodes(seo: String): List<Episode> {
        return client.request("anime/$seo").episodesQuery(client)
    }

    /**
     * Retrieves a paginated list of episodes for a specific anime using its SEO identifier.
     *
     * @param seo The SEO identifier of the anime.
     * @param page The page number to retrieve.
     * @return A [Pagination] object containing the retrieved episodes.
     */
    suspend fun getPaginationEpisodes(seo: String, page: Int? = null): Pagination<Episode> {
        return client.request("anime/$seo").episodesQuery(client, page)
    }

    /**
     * Retrieves the playback options for a specific episode using its SEO identifier.
     *
     * @param seo The SEO identifier of the episode.
     * @return A [Player] object with the available playback options.
     */
    suspend fun getPlayer(seo: String): Player {
        return client.request("ver/$seo").playerQuery()
    }
}
