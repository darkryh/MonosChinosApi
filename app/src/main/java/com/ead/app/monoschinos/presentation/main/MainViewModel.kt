@file:Suppress("unused")

package com.ead.app.monoschinos.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ead.app.monoschinos.presentation.main.util.EpisodePagingSource
import com.ead.lib.monoschinos.models.detail.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MainViewModel : ViewModel() {

    private val _episodes: MutableState<Flow<PagingData<Episode>>> = mutableStateOf(emptyFlow())
    val episodes: State<Flow<PagingData<Episode>>> = _episodes

    /**
     * Updates the episodes flow based on the provided SEO key.
     */
    fun loadEpisodes(seo: String) {
        _episodes.value = getEpisodesPaging(seo)
    }

    /**
     * Provides a Flow of PagingData for Episodes using EpisodePagingSource.
     */
    private fun getEpisodesPaging(seo: String): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EpisodePagingSource(seo) }
        ).flow
    }
}
