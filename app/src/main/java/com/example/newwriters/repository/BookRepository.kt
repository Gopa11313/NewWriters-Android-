package com.example.newwriters.repository

import com.example.newwriters.api.BookApi
import com.example.newwriters.api.MyApiRequest
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.response.BookResponse
import com.example.newwriters.ui.model.Book
import okhttp3.MultipartBody

class BookRepository:MyApiRequest() {
    val myapi=ServiceBuilder.buildServices(BookApi::class.java)

    suspend fun uploadBook(book:Book):BookResponse{
        return apiRequest {
            myapi.uploadBook(book)
        }
    }
    suspend fun uploadCoverPage(id:String,cover_page: MultipartBody.Part):BookResponse{
        return apiRequest {
            myapi.uploadCoverPage(ServiceBuilder.token!!,id,cover_page)
        }
    }
}