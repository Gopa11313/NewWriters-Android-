package com.example.newwriters.api

import com.example.newwriters.response.BookResponse
import com.example.newwriters.ui.model.Book
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface BookApi {
    @POST("upload/book")
    suspend fun uploadBook(@Body book:Book):Response<BookResponse>

    @Multipart
    @PUT("upload/image/{id}")
    suspend fun uploadCoverPage(
        @Header("Authorization") token:String,
        @Path("id") id:String,
        @Part cover_page: MultipartBody.Part
    ):Response<BookResponse>

    @Multipart
    @PUT("upload/book/{id}")
    suspend fun uploadBookFile(
        @Header("Authorization") token:String,
        @Path("id") id:String,
        @Part book: MultipartBody.Part
    ):Response<BookResponse>

    @GET("get/all/book")
    suspend fun getAllBook(@Header("Authorization") token:String):Response<BookResponse>
}