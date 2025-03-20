package com.serelik.surfbooks.domain

import com.serelik.surfbooks.domain.models.BookList
import retrofit2.http.Query

interface BookRepository {
    suspend fun searchBooks(query: String): BookList
}