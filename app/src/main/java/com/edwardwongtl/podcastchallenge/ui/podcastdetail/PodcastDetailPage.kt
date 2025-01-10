package com.edwardwongtl.podcastchallenge.ui.podcastdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.edwardwongtl.podcastchallenge.R
import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel
import com.edwardwongtl.podcastchallenge.ui.theme.FavouritePink
import com.edwardwongtl.podcastchallenge.ui.theme.PodcastChallengeTheme
import kotlinx.coroutines.launch

@Composable
fun PodcastDetailPage(
    viewModel: PodcastDetailViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    PodcastDetailUI(
        state.value,
        viewModel::setFavourite,
        { navController.popBackStack() },
        modifier
    )
}

@Composable
fun PodcastDetailUI(
    state: PodcastDetailState,
    setFavourite: (Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.podcast == null) {
        return
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Start)
                    .height(64.dp)
                    .clickable(onClick = onNavigateBack)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "Back"
                )
                Text("Back")
            }

            Text(
                text = state.podcast.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Text(
                text = state.podcast.publisher,
                fontSize = 16.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 4.dp)
            )

            AsyncImage(
                model = state.podcast.thumbnail,
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .padding(horizontal = 48.dp, vertical = 16.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Button(
                onClick = { setFavourite(!state.podcast.isFavourite) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FavouritePink
                )
            ) {
                Text(
                    text =
                    if (state.podcast.isFavourite) "Favourited" else "Favourite"
                )
            }

            Text(
                text = state.podcast.description,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
private fun PodcastDetailPreview() {
    val state = PodcastDetailState(
        podcast = PodcastModel(
            id = "1",
            title = "The Indicator from Planet Money",
            publisher = "NPR",
            thumbnail = "https://example.com/thumbnail.jpg",
            description = "The Ed Mylett Show showcases the greatest peak-performers across all industries in one place, sharing their journey, knowledge and thought leadership. With Ed Mylett and featured guests in almost every industry including business, health, collegiate and professional sports, politics, entrepreneurship, science, and entertainment, you'll find motivation, inspiration and practical steps to help you become the best version of you!",
            isFavourite = false
        )
    )

    val previewHandler = AsyncImagePreviewHandler {
        FakeImage()
    }

    PodcastChallengeTheme {
        CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
            PodcastDetailUI(
                state = state,
                setFavourite = {},
                onNavigateBack = {},
            )
        }
    }
}