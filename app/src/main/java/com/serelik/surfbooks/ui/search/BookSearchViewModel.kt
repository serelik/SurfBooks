package com.serelik.surfbooks.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.surfbooks.domain.models.BookItem
import com.serelik.surfbooks.domain.models.BookList
import com.serelik.surfbooks.domain.repository.BookRepository
import com.serelik.surfbooks.domain.repository.FavoriteBookRepository
import com.serelik.surfbooks.ui.favorite.BookItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val favoriteBookRepository: FavoriteBookRepository
) : ViewModel() {

    private val _booksStateFlow = MutableStateFlow<BookSearchUiState>(BookSearchUiState.EmptyQuery)
    val booksStateFlow = _booksStateFlow.asStateFlow()

    private val _favoriteBooksStateFlow = favoriteBookRepository.loadAllFavoriteBookIds()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val debounceSearch = MutableStateFlow("")

    private var searchJob: Job? = null

    init {
        debounceSearch.filter { it.isNotEmpty() }
            .debounce(2000L)
            .onEach(::searchBooks)
            .launchIn(viewModelScope)


        _favoriteBooksStateFlow
            .onEach {
                isFavoriteSets = it.toSet()
                updateSearchResultIfNeeded()
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(query: String) {
        viewModelScope.launch {
            if (query.isBlank())
                _booksStateFlow.emit(BookSearchUiState.EmptyQuery)
            _searchQuery.emit(query)
            debounceSearch.emit(query)
        }
    }

   private fun updateSearchResultIfNeeded() {
        val books = booksStateFlow.value

        if (books !is BookSearchUiState.Result)
            return

        val newList = books.bookList.map {
            val isFavorite = isFavoriteSets.contains(it.bookItem.id)
            if (isFavorite != it.isFavorite)
                it.copy(isFavorite = isFavorite)
            else it
        }

        viewModelScope.launch {

            _booksStateFlow.emit(BookSearchUiState.Result(newList))

        }


    }

    var isFavoriteSets = emptySet<String>()

    private fun mapBookList(bookList: BookList): List<BookItemUiModel> {
        return bookList.items.map {
            BookItemUiModel(it, isFavoriteSets.contains(it.id))
        }
    }

    private fun searchBooks(query: String) {

        searchJob?.cancel()

        searchJob = viewModelScope.launch {

            _booksStateFlow.emit(BookSearchUiState.Loading)

            try {
                val bookList = bookRepository.searchBooks(query)
                if (bookList.items.isNotEmpty())
                    _booksStateFlow.emit(BookSearchUiState.Result(mapBookList(bookList)))
                else _booksStateFlow.emit(BookSearchUiState.EmptyResult)
            } catch (e: Exception) {
                _booksStateFlow.emit(BookSearchUiState.Error)
            }

        }


    }

    fun onFavoriteClick(book: BookItemUiModel) {

        val isFavorite = book.isFavorite
        viewModelScope.launch {
            if (isFavorite)
                removeFromFavorite(book.bookItem.id)
            else
                addToFavorite(book = book.bookItem)
        }

    }

    private suspend fun addToFavorite(book: BookItem) {
        favoriteBookRepository.insert(book)
    }

    private suspend fun removeFromFavorite(id: String) {
        favoriteBookRepository.deleteById(id)
    }

    fun retrySearch() {
        searchBooks(debounceSearch.value)
    }

}