package com.edwardwongtl.podcastchallenge.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PodcastExtraApiModel(
    val url1: String = "",
    val url2: String = "",
    val url3: String = "",
    @Json(name = "spotify_url") val spotifyUrl: String = "",
    @Json(name = "youtube_url") val youtubeUrl: String = "",
    @Json(name = "linkedin_url") val linkedinUrl: String = "",
    @Json(name = "wechat_handle") val wechatHandle: String = "",
    @Json(name = "patreon_handle") val patreonHandle: String = "",
    @Json(name = "twitter_handle") val twitterHandle: String = "",
    @Json(name = "facebook_handle") val facebookHandle: String = "",
    @Json(name = "amazon_music_url") val amazonMusicUrl: String = "",
    @Json(name = "instagram_handle") val instagramHandle: String = "",
)
