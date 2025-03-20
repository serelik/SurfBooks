package com.serelik.surfbooks.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.moviedbcompose.navigation.destination.ID_KEY
import com.serelik.surfbooks.domain.models.BookItem
import com.serelik.surfbooks.domain.repository.BookRepository
import com.serelik.surfbooks.domain.repository.FavoriteBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository,
    private val favoriteBookRepository: FavoriteBookRepository
) : ViewModel() {

    private val _bookDetailsStateFlow =
        MutableStateFlow<BookDetailsUiState>(BookDetailsUiState.Loading)
    val bookDetailsStateFlow = _bookDetailsStateFlow.asStateFlow()

    private val _bookFavoriteStateFlow =
        MutableStateFlow<Boolean>(false)
    val bookFavoriteStateFlow = _bookFavoriteStateFlow.asStateFlow()


    private val bookId: String =
        savedStateHandle.get<String>(ID_KEY) ?: error("movieId must be not null")


    init {
        getBook()
        getFavoriteState()
    }

    private fun getBook() {
        viewModelScope.launch {
            try {
                val bookItem = bookRepository.getBook(bookId)
                _bookDetailsStateFlow.emit(BookDetailsUiState.Result(bookItem))

            } catch (e: Exception) {
                _bookDetailsStateFlow.emit(BookDetailsUiState.Error)
            }

        }
    }

    private fun getFavoriteState() {
        viewModelScope.launch {
            val isFavorite = favoriteBookRepository.loadById(bookId) != null
            _bookFavoriteStateFlow.emit(isFavorite)
        }
    }

    fun onFavoriteClick(book: BookItem) {

        val isFavorite = bookFavoriteStateFlow.value
        viewModelScope.launch {
            _bookFavoriteStateFlow.emit(!isFavorite)
            if (isFavorite)
                removeFromFavorite(bookId)
            else
                addToFavorite(book = book)
        }

    }

    private suspend fun addToFavorite(book: BookItem) {
        favoriteBookRepository.insert(book)
    }

    private suspend fun removeFromFavorite(id: String) {
        favoriteBookRepository.deleteById(id)
    }
}