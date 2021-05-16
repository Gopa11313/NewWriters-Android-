package com.example.newwriters.api

import com.example.newwriters.response.BookResponse
import com.example.newwriters.response.ReviewResponse
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.Review
import retrofit2.Response
import retrofit2.http.*

interface ReviewApi {

    @POST("add/review")
    suspend fun addReview(@Header("Authorization") token:String,@Body review: Review): Response<ReviewResponse>

    @GET("get/all/review/{bookid}")
    suspend fun GetAllReview(
        @Header("Authorization") token:String,
        @Path("bookid") bookid:String
    ):Response<ReviewResponse>
}