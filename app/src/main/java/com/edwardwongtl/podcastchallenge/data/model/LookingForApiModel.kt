package com.edwardwongtl.podcastchallenge.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LookingForApiModel(
    val guests: Boolean,
    val cohosts: Boolean,
    val sponsors: Boolean,
    @Json(name = "cross_promotion") val crossPromotion: Boolean
)
