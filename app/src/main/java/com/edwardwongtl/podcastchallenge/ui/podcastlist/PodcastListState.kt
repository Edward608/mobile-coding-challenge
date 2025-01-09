package com.edwardwongtl.podcastchallenge.ui.podcastlist

import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel

data class PodcastListState(
    val isLoading: Boolean = false,
    val podcasts: List<PodcastModel> = emptyList(),
    val error: Throwable? = null
)
