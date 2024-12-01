package com.ead.app.monoschinos.presentation.main.util

import com.ead.app.monoschinos.presentation.app.util.BasePagingSource
import com.ead.lib.monoschinos.MonosChinos
import com.ead.lib.monoschinos.models.detail.Episode

class EpisodePagingSource(
    private val seo : String,
) : BasePagingSource<Int, Episode>() {

    override suspend fun fetchData(params: LoadParams<Int>): List<Episode> {
        val currentPage = params.key ?: 1
        val pagination = MonosChinos.getPaginationEpisodes(seo,currentPage)
        return pagination.items
    }

    override fun getPreviousKey(currentKey: Int?): Int? {
        return if (currentKey != null && currentKey > 1) currentKey - 1 else null
    }

    override fun getNextKey(currentKey: Int?, data: List<Episode>): Int? {
        return if (data.isNotEmpty()) (currentKey ?: 1) + 1 else null
    }
}