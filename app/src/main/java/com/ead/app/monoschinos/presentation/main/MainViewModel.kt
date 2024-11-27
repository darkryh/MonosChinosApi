@file:Suppress("unused")

package com.ead.app.monoschinos.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.lib.monoschinos.MonosChinos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _result : MutableState<String?> = mutableStateOf(null)
    val result : State<String?> = _result

    fun exampleCombiningHomeAndPlayer() = viewModelScope.launch(Dispatchers.IO) {
        /*val home = MonosChinos
            .getHome()

        val firstChapter = home
            .lastChapters
            .firstOrNull() ?: return@launch

        val animePlay = MonosChinos
            .getPlayer(firstChapter.seo)

        _result.value = home.toString() + animePlay.toString()*/
    }

    fun exampleCombiningDirectoryAndDetail() = viewModelScope.launch(Dispatchers.IO) {
        val anime = MonosChinos
            .getSearchQuery("death note")
            .firstOrNull()

        val episodes = MonosChinos
            .getEpisodes(anime?.seo ?: return@launch)

        val player = MonosChinos.getPlayer(episodes.firstOrNull()?.seo ?: return@launch)

        _result.value = episodes.toString()
    }
}