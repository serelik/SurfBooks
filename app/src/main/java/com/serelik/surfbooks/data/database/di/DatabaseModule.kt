package com.serelik.surfbooks.data.database.di

import android.content.Context
import androidx.room.Room
import com.serelik.surfbooks.data.database.BooksAppDatabase
import com.serelik.surfbooks.data.database.dao.FavoriteBooksDao
import com.serelik.surfbooks.data.database.entities.DbContract
import com.serelik.surfbooks.data.database.repository.FavoriteBookRepositoryImpl
import com.serelik.surfbooks.data.network.repository.BookRepositoryImpl
import com.serelik.surfbooks.domain.repository.BookRepository
import com.serelik.surfbooks.domain.repository.FavoriteBookRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext applicationContext: Context): BooksAppDatabase =
            Room.databaseBuilder(
                applicationContext,
                BooksAppDatabase::class.java,
                DbContract.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

        @Provides
        fun provideQuoteDao(database: BooksAppDatabase): FavoriteBooksDao {
            return database.booksDao
        }
    }

    @Binds
    abstract fun bindFavoriteBookRepository(repository: FavoriteBookRepositoryImpl): FavoriteBookRepository

}