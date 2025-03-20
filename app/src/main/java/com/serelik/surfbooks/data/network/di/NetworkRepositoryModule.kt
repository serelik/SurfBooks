package com.serelik.surfbooks.data.network.di

import com.serelik.surfbooks.data.network.repository.BookRepositoryImpl
import com.serelik.surfbooks.domain.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkRepositoryModule {
    @Binds
    abstract fun bindBookRepository(repository: BookRepositoryImpl): BookRepository
}