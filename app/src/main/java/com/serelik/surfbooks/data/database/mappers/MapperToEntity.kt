package com.serelik.surfbooks.data.database.mappers

import com.serelik.surfbooks.data.database.entities.BookEntity
import com.serelik.surfbooks.domain.models.BookItem
import javax.inject.Inject

class MapperToEntity @Inject constructor() {
    fun fromBookItemToEntity(book: BookItem): BookEntity {
        return BookEntity(
            id = book.id,
            title = book.title,
            authors = book.authors.joinToString(","),
            publishedYear = book.publishedYear,
            description = book.description,
            imageUrl = book.imageUrl,
            timestamp = System.currentTimeMillis()
        )
    }
}
