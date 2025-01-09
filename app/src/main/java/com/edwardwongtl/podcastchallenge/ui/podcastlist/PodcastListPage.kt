package com.edwardwongtl.podcastchallenge.ui.podcastlist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel
import com.edwardwongtl.podcastchallenge.ui.theme.PodcastChallengeTheme

@Composable
fun PodcastListPage(
    viewModel: PodcastListViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    PodcastListUI(state.value, modifier)
}

@Composable
fun PodcastListUI(
    state: PodcastListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        itemsIndexed<PodcastModel>(
            items = state.podcasts,
            key = { _, podcast -> podcast.id },
            contentType = { _, _ -> "podcast" }
        ) { index, podcast ->
            PodcastListItem(podcast)
            if (index < state.podcasts.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun PodcastListItem(
    podcast: PodcastModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = podcast.thumbnail,
            contentDescription = podcast.title,
            modifier = Modifier
                .widthIn(min = 40.dp, max = 120.dp)
                .aspectRatio(1f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
        )

        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = podcast.title)
            Text(text = podcast.publisher)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PodcastListPreview() {
    val state = PodcastListState(
        podcasts = List(10) {
            PodcastModel(
                id = "$it",
                title = "Podcast $it",
                publisher = "Publisher $it",
            )
        }
    )

    PodcastChallengeTheme {
        Scaffold { innerPadding ->
            PodcastListUI(state, modifier = Modifier.padding(innerPadding))
        }
    }
}