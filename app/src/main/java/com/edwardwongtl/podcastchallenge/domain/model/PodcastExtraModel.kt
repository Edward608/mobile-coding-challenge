package com.edwardwongtl.podcastchallenge.domain.model

import com.edwardwongtl.podcastchallenge.data.model.PodcastExtraApiModel
import kotlinx.serialization.Serializable

@Serializable
data class PodcastExtraModel(
    val url1: String = "",
    val url2: String = "",
    val url3: String = "",
    val spotifyUrl: String = "",
    val youtubeUrl: String = "",
    val linkedinUrl: String = "",
    val wechatHandle: String = "",
    val patreonHandle: String = "",
    val twitterHandle: String = "",
    val facebookHandle: String = "",
    val amazonMusicUrl: String = "",
    val instagramHandle: String = "",
)

fun PodcastExtraApiModel.toModel() = PodcastExtraModel(
    url1 = url1,
    url2 = url2,
    url3 = url3,
    spotifyUrl = spotifyUrl,
    youtubeUrl = youtubeUrl,
    linkedinUrl = linkedinUrl,
    wechatHandle = wechatHandle,
    patreonHandle = patreonHandle,
    twitterHandle = twitterHandle,
    facebookHandle = facebookHandle,
    amazonMusicUrl = amazonMusicUrl,
    instagramHandle = instagramHandle,
)
