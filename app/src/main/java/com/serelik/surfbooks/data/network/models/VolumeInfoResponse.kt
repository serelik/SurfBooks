package com.serelik.surfbooks.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class VolumeInfoResponse(
    @SerialName("title")
    val title: String? = null,
    @SerialName("authors")
    val authors: List<String>? = null,
    @SerialName("publishedDate")
    val publishedDate: String? = null,
    @SerialName("imageLinks")
    val imageLinks: ImageLinksResponse? = null
)