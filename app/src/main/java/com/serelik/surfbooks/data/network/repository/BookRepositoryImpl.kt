package com.serelik.surfbooks.data.network.repository

import com.serelik.surfbooks.data.network.BooksApi
import com.serelik.surfbooks.data.network.mappers.BookItemNetworkMapper
import com.serelik.surfbooks.data.network.mappers.BookListNetworkMapper
import com.serelik.surfbooks.domain.repository.BookRepository
import com.serelik.surfbooks.domain.models.BookItem
import com.serelik.surfbooks.domain.models.BookList
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val mapper: BookListNetworkMapper,
    private val itemMapper: BookItemNetworkMapper
) : BookRepository {
    override suspend fun searchBooks(query: String): BookList {
        return booksApi.searchBooksList(query).let(mapper::convert)
    }

    override suspend fun getBook(id: String): BookItem {
        return booksApi.getBookByVolumeId(id).let {
            itemMapper.convert(it) ?: error("backend problem not all data")

        }
    }
}