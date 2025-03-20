package com.serelik.surfbooks.data.network

import com.serelik.surfbooks.data.network.models.BookItemResponse
import com.serelik.surfbooks.data.network.models.BooksListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun searchBooksList(
        @Query("q") query: String
    ): BooksListResponse


    @GET("volumes/{volumeId}")
    suspend fun getBookByVolumeId(
        @Path("volumeId") volumeId: String,
    ): BookItemResponse


}
