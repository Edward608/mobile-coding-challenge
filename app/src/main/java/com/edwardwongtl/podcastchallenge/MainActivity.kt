package com.edwardwongtl.podcastchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.edwardwongtl.podcastchallenge.ui.navigation.NavDestination
import com.edwardwongtl.podcastchallenge.ui.podcastdetail.PodcastDetailPage
import com.edwardwongtl.podcastchallenge.ui.podcastdetail.PodcastDetailViewModel
import com.edwardwongtl.podcastchallenge.ui.podcastlist.PodcastListPage
import com.edwardwongtl.podcastchallenge.ui.podcastlist.PodcastListViewModel
import com.edwardwongtl.podcastchallenge.ui.theme.PodcastChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PodcastChallengeTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = NavDestination.PodcastList) {
                    composable<NavDestination.PodcastList> {
                        val viewmodel: PodcastListViewModel = hiltViewModel()
                        PodcastListPage(
                            viewmodel,
                            onClick = { podcast ->
                                navController.navigate(
                                    NavDestination.PodcastDetail(
                                        podcast.id
                                    )
                                )
                            },
                        )
                    }

                    composable<NavDestination.PodcastDetail> {
                        val podcastId = it.toRoute<NavDestination.PodcastDetail>().podcastId
                        val viewmodel: PodcastDetailViewModel =
                            hiltViewModel<PodcastDetailViewModel, PodcastDetailViewModel.Factory> {
                                it.create(podcastId)
                            }

                        PodcastDetailPage(
                            viewmodel,
                            navController,
                        )
                    }

                }
            }
        }
    }
}