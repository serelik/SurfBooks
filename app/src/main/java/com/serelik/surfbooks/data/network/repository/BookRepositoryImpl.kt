package com.serelik.surfbooks.data.network.repository

import com.serelik.surfbooks.data.network.BooksApi
import com.serelik.surfbooks.data.network.mappers.BookListNetworkMapper
import com.serelik.surfbooks.domain.BookRepository
import com.serelik.surfbooks.domain.models.BookList
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val mapper: BookListNetworkMapper
) : BookRepository {
    override suspend fun searchBooks(query: String): BookList {
        return booksApi.searchBooksList(query).let(mapper::convert)
    }
}