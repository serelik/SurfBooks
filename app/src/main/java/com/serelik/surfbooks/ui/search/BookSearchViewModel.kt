package com.serelik.surfbooks.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.surfbooks.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _booksStateFlow = MutableStateFlow<BookSearchUiState>(BookSearchUiState.EmptyQuery)
    val booksStateFlow = _booksStateFlow.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val debounceSearch = MutableStateFlow("")

    private var searchJob: Job? = null

    init {
        debounceSearch.filter { it.isNotEmpty() }
            .debounce(2000L)
            .onEach(::searchBooks)
            .launchIn(viewModelScope)

    }

    fun onQueryChange(query: String) {
        viewModelScope.launch {
            _searchQuery.emit(query)
            debounceSearch.emit(query)
        }
    }

    private fun searchBooks(query: String) {

        searchJob = viewModelScope.launch {

            _booksStateFlow.emit(BookSearchUiState.Loading)

            try {
                val bookList = bookRepository.searchBooks(query)
                if (bookList.items.isNotEmpty())
                    _booksStateFlow.emit(BookSearchUiState.Result(bookList))
                else _booksStateFlow.emit(BookSearchUiState.EmptyResult)
            } catch (e: Exception) {
                _booksStateFlow.emit(BookSearchUiState.Error)
            }

        }


    }
}