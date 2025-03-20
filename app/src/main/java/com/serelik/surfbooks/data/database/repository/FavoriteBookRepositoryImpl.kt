package com.serelik.surfbooks.data.database.repository

import com.serelik.surfbooks.data.database.dao.FavoriteBooksDao
import com.serelik.surfbooks.data.database.mappers.MapperFromEntity
import com.serelik.surfbooks.data.database.mappers.MapperToEntity
import com.serelik.surfbooks.domain.models.BookItem
import com.serelik.surfbooks.domain.repository.FavoriteBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteBookRepositoryImpl @Inject constructor(
    private val mapperFromEntity: MapperFromEntity,
    private val mapperToEntity: MapperToEntity,
    private val dao: FavoriteBooksDao
) : FavoriteBookRepository {
    override fun loadAllFavoriteBooksFlow(): Flow<List<BookItem>> {
        return dao.loadAllFavoriteBooksFlow().map {
            it.map(mapperFromEntity::fromEntityToBookItem)
        }
    }

    override fun loadAllFavoriteBookIds(): Flow<List<String>> {
        return dao.loadAllFavoriteBookIds()
    }

    override suspend fun loadById(bookId: String): BookItem? {
        return dao.loadById(bookId)?.let(mapperFromEntity::fromEntityToBookItem)
    }

    override suspend fun insert(book: BookItem) {
        dao.insert(mapperToEntity.fromBookItemToEntity(book))
    }

    override suspend fun deleteById(bookId: String) {
        dao.deleteById(bookId)
    }
}