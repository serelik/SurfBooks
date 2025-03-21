package com.serelik.surfbooks.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageLinksResponse(
    @SerialName("smallThumbnail")
    val smallThumbnail: String? = null,
    @SerialName("thumbnail")
    val thumbnail: String? = null
)