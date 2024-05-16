package com.ead.lib.monoschinos.core.system.extensions

import com.ead.lib.monoschinos.models.structure.detail.AnimeDetailStructure
import com.ead.lib.monoschinos.models.structure.detail.EpisodeStructure
import com.ead.lib.monoschinos.models.structure.directory.AnimeStructure
import com.ead.lib.monoschinos.models.structure.home.HomeAnimeStructure
import com.ead.lib.monoschinos.models.structure.home.HomeChapterStructure
import com.ead.lib.monoschinos.models.structure.home.HomeStructure
import com.ead.lib.monoschinos.models.structure.player.PlayerStructure
import org.json.JSONObject

fun JSONObject.toHomeStructure() : HomeStructure {
    val homeChapterStructure = getJSONObject("homeChapterStructure")
    val homeAnimeStructure = getJSONObject("homeAnimeStructure")

    return HomeStructure(
        homeChapterStructure = HomeChapterStructure(
            classList = homeChapterStructure.getString("classList"),
            title = homeChapterStructure.getString("title"),
            number = homeChapterStructure.getString("number"),
            type = homeChapterStructure.getString("type"),
            image = homeChapterStructure.getString("image"),
            url = homeChapterStructure.getString("url")
        ),
        homeAnimeStructure = HomeAnimeStructure(
            classList = homeAnimeStructure.getString("classList"),
            title = homeAnimeStructure.getString("title"),
            type = homeAnimeStructure.getString("type"),
            image = homeAnimeStructure.getString("image"),
            url = homeAnimeStructure.getString("url")
        )
    )
}

fun JSONObject.toAnimeDetailStructure() : AnimeDetailStructure {
    val episodes = getJSONObject("episodes")

    return AnimeDetailStructure(
        title = getString("title"),
        alternativeTitle = getString("alternativeTitle"),
        status = getString("status"),
        coverImage = getString("coverImage"),
        profileImage = getString("profileImage"),
        release = getString("release"),
        synopsis = getString("synopsis"),
        genres = getString("genres"),
        episodes = EpisodeStructure(
            classList = episodes.getString("classList"),
            title = episodes.getString("title"),
            number = episodes.getString("number"),
            image = episodes.getString("image"),
            url = episodes.getString("url")
        ),
        url = getString("url")
    )
}

fun JSONObject.toAnimeStructure() : AnimeStructure {
    return AnimeStructure(
        classList = getString("classList"),
        title = getString("title"),
        type = getString("type"),
        year = getString("year"),
        image = getString("image"),
        url = getString("url")
    )
}

fun JSONObject.toPlayerStructure() : PlayerStructure {
    return PlayerStructure(
        optionsClassList = getString("optionsClassList"),
        optionAttribute = getString("optionAttribute"),
        downloadsClassList = getString("downloadsClassList"),
    )
}