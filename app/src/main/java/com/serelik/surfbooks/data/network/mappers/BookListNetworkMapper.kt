package com.serelik.surfbooks.data.network.mappers

import com.serelik.surfbooks.data.network.models.BooksListResponse
import com.serelik.surfbooks.domain.models.BookList
import javax.inject.Inject

class BookListNetworkMapper @Inject constructor(private val bookItemMapper: BookItemNetworkMapper) {
    fun convert(model: BooksListResponse): BookList =
        BookList(
            model.totalItems,
            model.items.orEmpty().mapNotNull(bookItemMapper::convert)
        )
}