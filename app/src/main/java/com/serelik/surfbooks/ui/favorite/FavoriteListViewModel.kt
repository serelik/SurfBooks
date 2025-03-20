package com.serelik.surfbooks.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serelik.surfbooks.domain.repository.FavoriteBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val favoriteBookRepository: FavoriteBookRepository
) : ViewModel() {
    val favoriteBooksStateFlow = favoriteBookRepository.loadAllFavoriteBooksFlow().map {
        it.map { book ->
            BookItemUiModel(book, true)
        }
    }

    fun onFavoriteClick(book: BookItemUiModel) {
        viewModelScope.launch {
            favoriteBookRepository.deleteById(book.bookItem.id)
        }

    }

}