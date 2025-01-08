package com.edwardwongtl.podcastchallenge.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PodcastListApiModel(
    val id: Int,
    val name: String,
    val total: Int,
    @Json(name = "has_next") val hasNext: Boolean,
    @Json(name = "has_previous") val hasPrevious: Boolean,
    @Json(name = "parent_id") val parentId: Int,
    @Json(name="page_number") val pageNumber: Int,
    @Json(name="listennotes_url") val listenNotesUrl: String,
    @Json(name="next_page_number") val nextPageNumber: Int,
    @Json(name="previous_page_number") val previousPageNumber: Int,
    val podcasts: List<PodcastApiModel>
)
