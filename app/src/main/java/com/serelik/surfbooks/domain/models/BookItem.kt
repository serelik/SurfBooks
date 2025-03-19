package com.serelik.surfbooks.domain.models

import java.time.LocalDate

data class BookItem(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedDate: LocalDate,
    val description: String,
    val imageUrl: String?
)