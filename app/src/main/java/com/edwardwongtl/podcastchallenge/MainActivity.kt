package com.edwardwongtl.podcastchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edwardwongtl.podcastchallenge.ui.navigation.NavDestination
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
                var pageTitle by remember { mutableStateOf("Podcasts") }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(pageTitle) },
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->

                    NavHost(navController, startDestination = NavDestination.PodcastList) {
                        composable<NavDestination.PodcastList> {
                            pageTitle = "Podcasts"
                            val viewmodel:PodcastListViewModel by viewModels()
                            PodcastListPage(viewmodel, Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}