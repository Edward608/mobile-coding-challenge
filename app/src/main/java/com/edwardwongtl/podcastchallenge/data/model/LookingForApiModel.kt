package com.edwardwongtl.podcastchallenge.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LookingForApiModel(
    val guests: Boolean = false,
    val cohosts: Boolean = false,
    val sponsors: Boolean = false,
    @Json(name = "cross_promotion") val crossPromotion: Boolean = false
)
