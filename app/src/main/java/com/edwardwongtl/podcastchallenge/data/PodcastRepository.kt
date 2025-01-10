package com.edwardwongtl.podcastchallenge.data

import android.content.SharedPreferences
import com.edwardwongtl.podcastchallenge.data.api.PodcastApi
import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel
import com.edwardwongtl.podcastchallenge.domain.model.toModel
import javax.inject.Inject

class PodcastRepository @Inject constructor(
    private val podcastApi: PodcastApi,
    private val sharedPreferences: SharedPreferences,
) {
    suspend fun getBestPodcasts(page: Int): Result<List<PodcastModel>> {
        return try {
            val response = podcastApi.getBestPodcasts(page)
            response.map {
                it.podcasts.map { podcast ->
                    podcast.toModel()
                        .copy(
                            isFavourite = sharedPreferences.getBoolean(podcast.id, false)
                        )
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPodcastDetail(podcastId: String): Result<PodcastModel> {
        return try {
            val response = podcastApi.getPodcast(podcastId)
            response.map { podcast ->
                podcast.toModel().copy(
                    isFavourite = sharedPreferences.getBoolean(podcastId, false)
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun setFavourite(podcastId: String, isFavourite: Boolean) {
        sharedPreferences.edit().putBoolean(podcastId, isFavourite).apply()
    }

    fun getFavourites(podcastIds: List<String>): Map<String, Boolean> {
        return podcastIds.map {
            it to sharedPreferences.getBoolean(it, false)
        }.toMap()
    }
}