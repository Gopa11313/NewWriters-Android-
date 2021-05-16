package com.example.newwriters.api

import com.example.newwriters.response.BookmarkResponse
import com.example.newwriters.response.ReviewResponse
import com.example.newwriters.ui.model.Bookmark
import com.example.newwriters.ui.model.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BookMarkApi {
    @POST("bookmark/book")
    suspend fun BookmarkBook(@Header("Authorization") token:String, @Body bookmark: Bookmark): Response<BookmarkResponse>
}