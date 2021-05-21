package com.example.newwriters.repository

import com.example.newwriters.api.BookApi
import com.example.newwriters.api.BoughtBookApi
import com.example.newwriters.api.MyApiRequest
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.response.BougthBookResponse
import com.example.newwriters.ui.model.BougthBooks

class BougthBookRepository:MyApiRequest() {
    val myapi= ServiceBuilder.buildServices(BoughtBookApi::class.java)

    suspend fun buyABook(bougthBooks: BougthBooks):BougthBookResponse{
        return apiRequest {
            myapi.buyABook(bougthBooks)
        }
    }
    suspend fun getallBougthBook(id:String):BougthBookResponse{
        return apiRequest {
            myapi.getallBougthBook(ServiceBuilder.token!!,id)
        }
    }
}