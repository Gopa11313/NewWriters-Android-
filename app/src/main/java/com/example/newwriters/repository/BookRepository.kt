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
    suspend fun UploadBookFile(id:String,book: MultipartBody.Part):BookResponse{
        return apiRequest {
            myapi.uploadBookFile(ServiceBuilder.token!!,id,book)
        }
    }
    suspend fun getAllBook():BookResponse{
        return apiRequest {
            myapi.getAllBook(ServiceBuilder.token!!)
        }
    }
    suspend fun AllTopRatedBook():BookResponse{
        return apiRequest {
            myapi.getAllTopRatedBook(ServiceBuilder.token!!)
        }
    }
    suspend fun topSoldBook():BookResponse{
        return apiRequest {
            myapi.getAllSoldBook(ServiceBuilder.token!!)
        }
    }
    suspend fun getBookID(id:String):BookResponse{
        return apiRequest {
            myapi.getBookId(ServiceBuilder.token!!,id)
        }
    }
}