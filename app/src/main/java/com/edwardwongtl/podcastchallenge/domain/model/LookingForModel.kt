package com.edwardwongtl.podcastchallenge.domain.model

import com.edwardwongtl.podcastchallenge.data.model.LookingForApiModel
import kotlinx.serialization.Serializable

@Serializable
data class LookingForModel(
    val guests: Boolean = false,
    val cohosts: Boolean = false,
    val sponsors: Boolean = false,
    val crossPromotion: Boolean = false
)

fun LookingForApiModel.toModel() = LookingForModel(
    guests = guests,
    cohosts = cohosts,
    sponsors = sponsors,
    crossPromotion = crossPromotion,
)
