package com.serelik.surfbooks.ui.search

import com.serelik.surfbooks.domain.models.BookList

sealed interface BookSearchUiState {
    data object EmptyQuery: BookSearchUiState
    data object Loading: BookSearchUiState
    data object Error: BookSearchUiState
    data object EmptyResult: BookSearchUiState
    data class Result(val bookList: BookList): BookSearchUiState
}