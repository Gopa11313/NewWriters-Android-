package com.example.newwriters.repository

import com.example.newwriters.api.BookApi
import com.example.newwriters.api.MyApiRequest
import com.example.newwriters.api.ReviewApi
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.response.ReviewResponse
import com.example.newwriters.ui.model.Review

class ReviewRepository:MyApiRequest() {
    val myapi= ServiceBuilder.buildServices(ReviewApi::class.java)

    suspend fun addReiew(review:Review):ReviewResponse{
        return apiRequest{
            myapi.addReview(ServiceBuilder.token!!,review)
        }
    }

    suspend fun getallReview(bookid:String):ReviewResponse{
        return apiRequest {
            myapi.GetAllReview(ServiceBuilder.token!!,bookid)
        }
    }
}