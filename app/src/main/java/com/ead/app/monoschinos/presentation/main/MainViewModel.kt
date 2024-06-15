package com.ead.app.monoschinos.presentation.main

import android.content.Context
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

    fun exampleCombiningHomeAndPlayer(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        val home = MonosChinos
            .builder(context)
            .homePage()
            .getNullable()

        val firstChapter = home
            ?.lastChapters
            ?.first() ?: return@launch

        val animePlay = MonosChinos
            .builder(context)
            .playerPage(firstChapter.seo)
            .getNullable()

        _result.value = home.toString() + animePlay.toString()
    }

    fun exampleCombiningDirectoryAndDetail(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        val anime = MonosChinos
            .builder(context)
            .searchPage("death note")
            .getOrEmpty()
            .firstOrNull()

        val selectedAnime = anime ?: MonosChinos
            .builder(context)
            .directoryPage(1)
            .getOrEmpty()
            .first()

        val animeDetail = MonosChinos
            .builder(context)
            .animeDetailPage(selectedAnime.seo)
            .getNullable()

        val episodes = MonosChinos
            .builder(context)
            .chaptersPage(selectedAnime.seo)
            .getOrEmpty()


        _result.value = anime.toString() + animeDetail.toString() + episodes.toString()
    }
}