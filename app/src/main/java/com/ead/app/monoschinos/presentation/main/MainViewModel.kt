package com.ead.app.monoschinos.presentation.main

import android.util.Log
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

    fun test() {
        viewModelScope.launch(Dispatchers.IO) {
            val home = MonosChinos
                .builder()
                .homePage()
                .get()?: return@launch

            val firstAnimeHome = home.lastChapters.first()

            val result = MonosChinos
                .builder()
                .playerPage(firstAnimeHome.seo)
                .get()

            _result.value = result.toString()
            Log.d("test", "test: $result")
        }
    }
}