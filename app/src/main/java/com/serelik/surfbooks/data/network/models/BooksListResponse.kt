package com.serelik.surfbooks.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksListResponse(
    @SerialName("totalItems")
    val totalItems: Int,
    @SerialName("items")
    val items: List<BookItemResponse>? = null
)