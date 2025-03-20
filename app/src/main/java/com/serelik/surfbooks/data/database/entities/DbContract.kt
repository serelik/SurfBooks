package com.serelik.surfbooks.data.database.entities

import android.provider.BaseColumns

object DbContract {
    const val DATABASE_NAME = "BooksApp.db"

    object Books {
        const val TABLE_NAME = "favorite_books"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_PUBLISHED_YEAR = "published_year"
        const val COLUMN_NAME_IMAGE_URL = "image_url"
        const val COLUMN_NAME_AUTHORS = "authors"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }


}