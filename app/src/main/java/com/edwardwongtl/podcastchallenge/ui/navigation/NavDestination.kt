package com.edwardwongtl.podcastchallenge.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavDestination(val route: String) {
    @Serializable
    object PodcastList : NavDestination("podcastList")

    @Serializable
    data class PodcastDetail(val podcastId: String): NavDestination("podcastDetail/$podcastId")
}