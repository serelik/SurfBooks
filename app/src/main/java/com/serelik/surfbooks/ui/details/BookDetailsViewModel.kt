package com.serelik.surfbooks.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.moviedbcompose.navigation.destination.ID_KEY
import com.serelik.surfbooks.domain.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val bookRepository: BookRepository
) : ViewModel() {

    private val _bookDetailsStateFlow =
        MutableStateFlow<BookDetailsUiState>(BookDetailsUiState.Loading)
    val bookDetailsStateFlow = _bookDetailsStateFlow.asStateFlow()

    private val bookId: String =
        savedStateHandle.get<String>(ID_KEY) ?: error("movieId must be not null")


    init {
        getBook()
    }

    private fun getBook() {
        viewModelScope.launch {
            try {
                Log.e("check", "in try and bookid $bookId")
                val bookItem = bookRepository.getBook(bookId)
                Log.e(
                    "check",
                    "${bookItem.id}, ${bookItem.imageUrl}, ${bookItem.title}, ${bookItem.authors}"
                )
                _bookDetailsStateFlow.emit(BookDetailsUiState.Result(bookItem))

            } catch (e: Exception) {
                _bookDetailsStateFlow.emit(BookDetailsUiState.Error)
            }

        }
    }
}