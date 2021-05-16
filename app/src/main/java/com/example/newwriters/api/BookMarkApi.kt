package com.example.newwriters.api

import com.example.newwriters.response.BookmarkResponse
import com.example.newwriters.response.ReviewResponse
import com.example.newwriters.ui.model.Bookmark
import com.example.newwriters.ui.model.Review
import retrofit2.Response
import retrofit2.http.*

interface BookMarkApi {
    @POST("bookmark/book")
    suspend fun BookmarkBook(@Header("Authorization") token:String, @Body bookmark: Bookmark): Response<BookmarkResponse>

    @GET("get/all/bookmark/boook/{userid}")
    suspend fun getBookamrkedBook(
        @Header("Authorization") token:String,
        @Path("userid") userid:String
    ):Response<BookmarkResponse>

    @DELETE("delete/bookmark/book/{id}")
    suspend fun deleteBookmarkedBook(
        @Header("Authorization") token:String,
        @Path("id") id:String
    ):Response<BookmarkResponse>
}