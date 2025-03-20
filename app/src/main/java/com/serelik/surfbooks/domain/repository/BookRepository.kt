package com.serelik.surfbooks.domain.repository

import com.serelik.surfbooks.domain.models.BookItem
import com.serelik.surfbooks.domain.models.BookList

interface BookRepository {
    suspend fun searchBooks(query: String): BookList
    suspend fun getBook(id: String): BookItem
}