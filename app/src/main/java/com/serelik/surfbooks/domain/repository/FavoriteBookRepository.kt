package com.serelik.surfbooks.domain.repository

import com.serelik.surfbooks.domain.models.BookItem
import kotlinx.coroutines.flow.Flow

interface FavoriteBookRepository {
    fun loadAllFavoriteBooksFlow(): Flow<List<BookItem>>

    fun loadAllFavoriteBookIds(): Flow<List<String>>

    suspend fun loadById(bookId: String): BookItem?

    suspend fun insert(book: BookItem)

    suspend fun deleteById(bookId: String)
}