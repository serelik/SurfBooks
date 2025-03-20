package com.serelik.surfbooks.ui.details

import com.serelik.surfbooks.domain.models.BookItem

sealed interface BookDetailsUiState {
    data object Loading: BookDetailsUiState
    data object Error: BookDetailsUiState
    data class Result(val bookItem: BookItem): BookDetailsUiState
}