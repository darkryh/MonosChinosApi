package com.ead.app.monoschinos.presentation.main

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

    fun exampleCombiningHomeAndPlayer() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val home = MonosChinos
                .builder()
                .homePage()
                .get()

            val firstChapter = home
                ?.lastChapters
                ?.first() ?: return@launch

            val animePlay = MonosChinos
                .builder()
                .playerPage(firstChapter.seo)
                .get()

            _result.value = animePlay.toString()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

    fun exampleCombiningDirectoryAndDetail() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val anime = MonosChinos
                .builder()
                .directoryPage(1)
                .get()
                .firstOrNull()

            val selectedAnime = anime ?: MonosChinos
                .builder()
                .searchPage("dragon ball")
                .get()
                .first()

            val animeDetail = MonosChinos
                .builder()
                .animeDetailPage(selectedAnime.seo)
                .get()

            _result.value = animeDetail.toString()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }
}