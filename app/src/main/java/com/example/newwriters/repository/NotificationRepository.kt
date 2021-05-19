package com.example.newwriters.repository

import com.example.newwriters.api.BookMarkApi
import com.example.newwriters.api.MyApiRequest
import com.example.newwriters.api.NoticationApi
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.response.NoticationResponse
import com.example.newwriters.ui.model.Notification

class NotificationRepository:MyApiRequest() {
    val myapi= ServiceBuilder.buildServices(NoticationApi::class.java)
    suspend fun addNotification(notification: Notification):NoticationResponse{
        return apiRequest {
            myapi.addNotification(ServiceBuilder.token!!,notification)
        }
    }
    suspend fun getAllNotif():NoticationResponse{
        return apiRequest {
            myapi.getallNotification(ServiceBuilder.token!!)
        }
    }
    suspend fun checked(id:String):NoticationResponse{
        return apiRequest {
            myapi.updateChecked(ServiceBuilder.token!!,id!!)
        }
    }
}