package com.serelik.surfbooks.ui.favorite

import com.serelik.surfbooks.domain.models.BookItem

data class BookItemUiModel(
    val bookItem: BookItem,
    val isFavorite: Boolean
)