package com.edwardwongtl.podcastchallenge.data.api

import com.edwardwongtl.podcastchallenge.data.model.PodcastApiModel
import com.edwardwongtl.podcastchallenge.data.model.PodcastListApiModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

const val BASE_URL = "https://listen-api-test.listennotes.com/api/v2"

interface PodcastApi {
    @GET("/best_podcasts")
    suspend fun getBestPodcasts(@Query("page") page: Int): Result<PodcastListApiModel>

    @GET("/podcasts/{id}")
    suspend fun getPodcast(@Path("id") id: String): Result<PodcastApiModel>
}