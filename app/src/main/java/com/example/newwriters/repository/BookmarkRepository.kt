package com.example.newwriters.repository

import com.example.newwriters.api.BookApi
import com.example.newwriters.api.BookMarkApi
import com.example.newwriters.api.MyApiRequest
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.response.BookmarkResponse
import com.example.newwriters.ui.model.Bookmark

class BookmarkRepository:MyApiRequest() {
    val myapi= ServiceBuilder.buildServices(BookMarkApi::class.java)

    suspend fun BookmarkBook(bookmark: Bookmark):BookmarkResponse{
        return apiRequest {
            myapi.BookmarkBook(ServiceBuilder.token!!,bookmark)
        }
    }
    suspend fun getAllBookamarkedBook(userId:String):BookmarkResponse{
        return apiRequest {
            myapi.getBookamrkedBook(ServiceBuilder.token!!,userId)
        }
    }
}