package com.ead.lib.monoschinos.scrapper

import com.ead.lib.monoschinos.core.Api
import com.ead.lib.monoschinos.models.player.Player
import org.jsoup.Jsoup
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


val player = Api.getPlayerStructure()

/**
 * This es player method to get the player data
 * from the web page MonosChinos it's compose with
 * Jsoup to extract the data from the page and a api call
 * to get the structure queries updated of the page
 *
 * Including a decoder to handle the encrypted data in player
 */
@OptIn(ExperimentalEncodingApi::class)
fun String.player() : Player {
    /**
     * Getting the player page data with jsoup
     */
    val playerPage = Jsoup.parse(this)



    return Player(
        /**
         * Mapping the whole jsoup data into the player data
         */
        options = playerPage.select(player.optionsClassList).map { element ->
            Base64.decode(element.attr(player.optionAttribute)).toString(Charsets.UTF_8)
        },
        downloads = playerPage.select(player.downloadsClassList).map { element ->
            element.attr("href")
        }
    )
}