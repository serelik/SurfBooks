package com.serelik.surfbooks.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class VolumeInfoResponse(
    @SerialName("title")
    val title: String,
    @SerialName("authors")
    val authors: List<String>,
    @SerialName("publishedDate")
    val publishedDate: String,
    @SerialName("description")
    val description: String,
    @SerialName("imageLinks")
    val imageLinks: ImageLinksResponse?
)