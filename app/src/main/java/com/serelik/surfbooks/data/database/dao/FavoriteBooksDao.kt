package com.serelik.surfbooks.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serelik.surfbooks.data.database.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBooksDao {
    @Query("SELECT * FROM favorite_books ORDER BY timestamp DESC")
    fun loadAllFavoriteBooksFlow(): Flow<List<BookEntity>>

    @Query("SELECT _id FROM favorite_books")
    fun loadAllFavoriteBookIds(): Flow<List<String>>

    @Query("SELECT * FROM favorite_books WHERE _id = :bookId")
    suspend fun loadById(bookId: String): BookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Query("DELETE FROM favorite_books WHERE _id = :bookId")
    suspend fun deleteById(bookId: String)
}
