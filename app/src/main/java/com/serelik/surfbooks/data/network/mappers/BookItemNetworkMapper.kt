package com.serelik.surfbooks.data.network.mappers

import com.serelik.surfbooks.data.network.models.BookItemResponse
import com.serelik.surfbooks.domain.models.BookItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class BookItemNetworkMapper @Inject constructor() {
    fun convert(model: BookItemResponse): BookItem {
        val localDate = LocalDate.parse(model.volumeInfo.publishedDate, formatter)
        return BookItem(
            id = model.id,
            title = model.volumeInfo.title,
            authors = model.volumeInfo.authors,
            publishedDate = localDate,
            description = model.volumeInfo.description,
            imageUrl = model.volumeInfo.imageLinks?.thumbnail
        )
    }

    companion object {
        private val formatter by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
    }
}