package com.serelik.surfbooks.domain.models

import java.time.LocalDate

data class BookItem(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedYear: String?,
    val imageUrl: String?
)