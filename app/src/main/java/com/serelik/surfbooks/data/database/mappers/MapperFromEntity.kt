package com.serelik.surfbooks.data.database.mappers

import com.serelik.surfbooks.data.database.entities.BookEntity
import com.serelik.surfbooks.domain.models.BookItem
import javax.inject.Inject

class MapperFromEntity @Inject constructor() {
    fun fromEntityToBookItem(entity: BookEntity): BookItem {
        return BookItem(
            id = entity.id,
            title = entity.title,
            authors = entity.authors.split(","),
            publishedYear = entity.publishedYear,
            description = entity.description,
            imageUrl = entity.imageUrl
        )
    }
}
