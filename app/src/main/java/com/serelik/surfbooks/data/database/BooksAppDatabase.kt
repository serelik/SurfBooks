package com.serelik.surfbooks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serelik.surfbooks.data.database.dao.FavoriteBooksDao
import com.serelik.surfbooks.data.database.entities.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1
)
abstract class BooksAppDatabase : RoomDatabase() {
    abstract val booksDao: FavoriteBooksDao
}