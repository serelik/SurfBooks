package com.serelik.surfbooks.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookItemResponse(
    @SerialName("id")
    val id: String?,
    @SerialName("volumeInfo")
    val volumeInfo: VolumeInfoResponse? = null
)