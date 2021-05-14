package com.example.newwriters.api

import com.example.newwriters.response.BookResponse
import com.example.newwriters.response.ReviewResponse
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewApi {
    @POST("add/review")
    suspend fun addReview(@Body review: Review): Response<ReviewResponse>
}