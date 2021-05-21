package com.example.newwriters.api

import com.example.newwriters.response.BookResponse
import com.example.newwriters.response.BougthBookResponse
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.BougthBooks
import retrofit2.Response
import retrofit2.http.*

interface BoughtBookApi {
    @POST("buying/books")
    suspend fun buyABook(@Body bougthBooks: BougthBooks): Response<BougthBookResponse>

    @GET("get/all/bougthBook/{id}")
    suspend fun getallBougthBook(
        @Header("Authorization") token:String,
        @Path("id") id:String,
    ):Response<BougthBookResponse>
}