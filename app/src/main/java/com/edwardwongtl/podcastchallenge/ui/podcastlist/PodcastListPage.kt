package com.edwardwongtl.podcastchallenge.ui.podcastlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel
import com.edwardwongtl.podcastchallenge.ui.theme.FavouritePink
import com.edwardwongtl.podcastchallenge.ui.theme.PodcastChallengeTheme
import kotlinx.coroutines.launch

@Composable
fun PodcastListPage(
    viewModel: PodcastListViewModel,
    onClick: (PodcastModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    PodcastListUI(state.value, onClick, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastListUI(
    state: PodcastListState,
    onClick: (PodcastModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Podcasts",
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        state.error?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it.message.orEmpty())
            }
        }

        if (state.isLoading) {
            Box(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .widthIn(min = 36.dp, max = 100.dp)
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            itemsIndexed<PodcastModel>(
                items = state.podcasts,
                key = { _, podcast -> podcast.id },
                contentType = { _, _ -> "podcast" }
            ) { index, podcast ->
                PodcastListItem(podcast, onClick)
                if (index < state.podcasts.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PodcastListItem(
    podcast: PodcastModel,
    onClick: (PodcastModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(podcast) }
    ) {
        AsyncImage(
            model = podcast.thumbnail,
            contentDescription = "Thumbnail of ${podcast.title}",
            modifier = Modifier
                .widthIn(min = 40.dp, max = 120.dp)
                .aspectRatio(1f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
        )

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = podcast.title,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = podcast.publisher,
                color = Color.LightGray,
                fontStyle = FontStyle.Italic,
            )


            Text(
                text = if (podcast.isFavourite) "Favourited" else "",
                color = FavouritePink,
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PodcastListPreview() {
    val state = PodcastListState(
        podcasts = List(10) {
            PodcastModel(
                id = "$it",
                title = "Podcast $it",
                publisher = "Publisher $it",
                isFavourite = it % 2 == 1
            )
        }
    )

    val previewHandler = AsyncImagePreviewHandler {
        FakeImage()
    }

    PodcastChallengeTheme {
        CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
            PodcastListUI(
                state,
                onClick = {},
            )
        }
    }
}