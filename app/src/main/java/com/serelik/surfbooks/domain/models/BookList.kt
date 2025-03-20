package com.serelik.surfbooks.domain.models

data class BookList(
    val totalItems: Int,
    val items: List<BookItem>
)