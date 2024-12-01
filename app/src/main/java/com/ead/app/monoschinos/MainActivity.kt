package com.ead.app.monoschinos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.ead.app.monoschinos.presentation.main.MainViewModel
import com.ead.app.monoschinos.presentation.theme.MonosChinosTheme
import com.ead.lib.monoschinos.models.detail.Episode

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonosChinosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        EpisodeList(
                            viewModel = viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeList(viewModel: MainViewModel, modifier: Modifier = Modifier) {

    viewModel.loadEpisodes("one-piece")

    val episodesFlow = viewModel.episodes.value
    val episodes = episodesFlow.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier) {
        items(episodes.itemCount) { index ->
            val episode = episodes[index]
            if (episode != null) {
                EpisodeItem(episode)
            }
        }

        when (episodes.loadState.append) {
            is LoadState.Loading -> {
                item { LoadingIndicator() }
            }
            is LoadState.Error -> {
                item { ErrorIndicator("Error loading episodes") }
            }
            else -> {}
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(120.dp)
                .width(80.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            model = episode.image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(
            modifier = Modifier.weight(1f)
                .padding(
                    horizontal = 16.dp
                )
        ) {
            Text(text = "Seo ${episode.seo}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Episode number ${episode.number}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Url ${episode.url}")
        }
    }
}

@Composable
fun LoadingIndicator() {
    Text(text = "Loading...")
}

@Composable
fun ErrorIndicator(message: String) {
    Text(text = message)
}