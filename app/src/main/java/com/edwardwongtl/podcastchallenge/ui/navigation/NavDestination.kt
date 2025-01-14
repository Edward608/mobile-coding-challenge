package com.edwardwongtl.podcastchallenge.ui.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.edwardwongtl.podcastchallenge.domain.model.PodcastModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed class NavDestination(val route: String) {
    @Serializable
    object PodcastList : NavDestination("podcastList")

    @Serializable
    data class PodcastDetail(val podcast: PodcastModel) :
        NavDestination("podcastDetail/${podcast.id}")
}

object CustomNavType {
    val PodcastType = object : NavType<PodcastModel>(isNullableAllowed = false) {
        override fun get(
            bundle: Bundle,
            key: String
        ): PodcastModel? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(key, PodcastModel::class.java)
            } else {
                bundle.getSerializable(key) as? PodcastModel
            }
        }

        override fun parseValue(value: String): PodcastModel {
            return Json.decodeFromString<PodcastModel>(value)
        }

        override fun serializeAsValue(value: PodcastModel): String {
            // Output must be Uri encoded in order for navigation to work properly
            return Uri.encode(Json.encodeToString(PodcastModel.serializer(), value))
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: PodcastModel
        ) {
            bundle.putSerializable(key, value)
        }
    }
}