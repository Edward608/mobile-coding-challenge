package com.edwardwongtl.podcastchallenge.ui.podcastdetail

import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel

data class PodcastDetailState(
    val isLoading: Boolean = false,
    val podcast: PodcastModel? = null,
    val error: Throwable? = null
)
