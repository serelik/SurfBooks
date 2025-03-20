package com.serelik.surfbooks.domain.models

data class BookItem(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedYear: String?,
    val description: String,
    val imageUrl: String?
)