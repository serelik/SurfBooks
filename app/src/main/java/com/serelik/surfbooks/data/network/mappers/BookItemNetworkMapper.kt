package com.serelik.surfbooks.data.network.mappers

import com.serelik.surfbooks.data.network.models.BookItemResponse
import com.serelik.surfbooks.domain.models.BookItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class BookItemNetworkMapper @Inject constructor() {
    fun convert(model: BookItemResponse): BookItem? {

        if (model.volumeInfo == null)
            return null

        val publishedYear = try {
            LocalDate.parse(model.volumeInfo.publishedDate, formatter).year.toString()
        } catch (e: Exception) {
            model.volumeInfo.publishedDate
        }

            return BookItem(
                id = model.id ?: return null,
                title = model.volumeInfo.title?: return null,
                authors = model.volumeInfo.authors?: return null,
                publishedYear = publishedYear.orEmpty(),
                imageUrl = convertImage(model.volumeInfo.imageLinks?.thumbnail)
            )
    }

    private fun convertImage(imageUrl: String?): String? {
        imageUrl ?: return null
        return imageUrl.replace("http://", "https://")
    }

    companion object {
        private val formatter by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
    }
}