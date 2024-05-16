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
import java.io.IOException

class MainViewModel : ViewModel() {

    private val _result : MutableState<String?> = mutableStateOf(null)
    val result : State<String?> = _result

    fun exampleCombiningHomeAndPlayer(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val home = MonosChinos
                .builder(context)
                .homePage()
                .get()

            val firstChapter = home
                ?.lastChapters
                ?.first() ?: return@launch

            val animePlay = MonosChinos
                .builder(context)
                .playerPage(firstChapter.seo)
                .get()

            _result.value = home.toString() + animePlay.toString()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

    fun exampleCombiningDirectoryAndDetail(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val anime = MonosChinos
                .builder(context)
                .directoryPage(1)
                .get()
                .firstOrNull()

            val selectedAnime = anime ?: MonosChinos
                .builder(context)
                .searchPage("dragon ball")
                .get()
                .first()

            val animeDetail = MonosChinos
                .builder(context)
                .animeDetailPage(selectedAnime.seo)
                .get()

            val episodes = MonosChinos
                .builder(context)
                .chaptersPage(selectedAnime.seo)
                .get()


            _result.value = anime.toString() + animeDetail.toString() + episodes.toString()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }
}