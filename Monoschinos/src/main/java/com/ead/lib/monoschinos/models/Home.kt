package com.ead.lib.monoschinos.models

import com.ead.lib.monoschinos.models.home.HomeChapter
import com.ead.lib.monoschinos.models.home.HomeAnime

data class Home(
    val lastChapters : List<HomeChapter>,
    val recentSeries : List<HomeAnime>
)