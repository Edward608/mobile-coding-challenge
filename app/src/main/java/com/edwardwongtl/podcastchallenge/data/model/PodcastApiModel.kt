package com.edwardwongtl.podcastchallenge.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PodcastApiModel(
    val id: String,
    val rss: String,
    val type: String,
    val email: String,
    val extra: PodcastExtraApiModel,
    val image: String,
    val title: String,
    val country: String,
    val website: String,
    @Json(name="genre_ids") val genreIds: List<Int>,
    @Json(name="itunes_id") val itunesId: Int,
    val thumbnail: String,
    @Json(name = "is_claimed") val isClaimed: Boolean,
    val description: String,
    @Json(name = "looking_for") val lookingFor: LookingForApiModel,
    @Json(name = "has_sponsors") val hasSponsors: Boolean,
    @Json(name = "listen_score") val listenScore: Int,
    @Json(name="total_episodes") val totalEpisodes: Int,
    @Json(name="listennotes_url") val listenNotesUrl: String,
    @Json(name = "audio_length_sec") val audioLengthSec: Int,
    @Json(name="explicit_content") val explicitContent: Boolean,
    @Json(name = "latest_episode_id") val latestEpisodeId: String,
    @Json(name = "latest_pub_date_ms") val latestPubDateMs: Long,
    @Json(name = "earliest_pub_date_ms") val earliestPubDateMs: Long,
    @Json(name = "has_guest_interviews") val hasGuestInterviews: Boolean,
    @Json(name = "update_frequency_hours") val updateFrequencyHours: Int,
    @Json(name = "listen_score_global_rank") val listenScoreGlobalRank: String,
)
