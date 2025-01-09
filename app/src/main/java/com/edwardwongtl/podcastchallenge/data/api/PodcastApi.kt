package com.edwardwongtl.podcastchallenge.data.api

import com.edwardwongtl.podcastchallenge.data.model.PodcastApiModel
import com.edwardwongtl.podcastchallenge.data.model.PodcastListApiModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

const val BASE_URL = "https://listen-api-test.listennotes.com/"

interface PodcastApi {
    @GET("/api/v2/best_podcasts")
    suspend fun getBestPodcasts(@Query("page") page: Int): Result<PodcastListApiModel>

    @GET("/api/v2/podcasts/{id}")
    suspend fun getPodcast(@Path("id") id: String): Result<PodcastApiModel>
}