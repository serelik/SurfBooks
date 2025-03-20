package com.serelik.surfbooks.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = DbContract.Books.TABLE_NAME,
    indices = [Index(DbContract.Books.COLUMN_NAME_ID)]
)
data class BookEntity(
    @PrimaryKey
    @ColumnInfo(name = DbContract.Books.COLUMN_NAME_ID)
    val id: String,
    @ColumnInfo(name = DbContract.Books.COLUMN_NAME_TITLE)
    val title: String,
    @ColumnInfo(name = DbContract.Books.COLUMN_NAME_AUTHORS)
    val authors: String,
    @ColumnInfo(name = DbContract.Books.COLUMN_NAME_PUBLISHED_YEAR)
    val publishedYear: String?,
    @ColumnInfo(name = DbContract.Books.COLUMN_NAME_DESCRIPTION)
    val description: String,
    @ColumnInfo(name = DbContract.Books.COLUMN_NAME_IMAGE_URL)
    val imageUrl: String?,
    @ColumnInfo(name = DbContract.Books.COLUMN_NAME_TIMESTAMP)
    val timestamp: Long
)