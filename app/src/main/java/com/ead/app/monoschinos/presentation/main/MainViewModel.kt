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
            .builder(context)
            .homePage()
            .getNullable()

        val firstChapter = home
            ?.lastChapters
            ?.firstOrNull() ?: return@launch

        val animePlay = MonosChinos
            .builder(context)
            .playerPage(firstChapter.seo)
            .getNullable()

        _result.value = home.toString() + animePlay.toString()*/
    }

    fun exampleCombiningDirectoryAndDetail() = viewModelScope.launch(Dispatchers.IO) {
        val anime = MonosChinos
            .searchQuery("death note")
            .firstOrNull()

        val episodes = MonosChinos
            .episodes(anime?.seo ?: return@launch)

        val player = MonosChinos
            .player(episodes.firstOrNull()?.seo ?: return@launch)

        _result.value = episodes.toString()
    }
}