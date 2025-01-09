package com.edwardwongtl.podcastchallenge.data

import com.edwardwongtl.podcastchallenge.data.api.PodcastApi
import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel
import com.edwardwongtl.podcastchallenge.domain.model.toModel
import javax.inject.Inject

class PodcastRepository @Inject constructor(
    private val podcastApi: PodcastApi
) {
    suspend fun getBestPodcasts(page: Int): Result<List<PodcastModel>> {
        try {
            val response = podcastApi.getBestPodcasts(page)
            return response.map {
                it.podcasts.map { it.toModel() }
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}