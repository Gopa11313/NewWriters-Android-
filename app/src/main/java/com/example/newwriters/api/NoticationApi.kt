package com.example.newwriters.api

import com.example.newwriters.response.NoticationResponse
import com.example.newwriters.ui.model.Notification
import retrofit2.Response
import retrofit2.http.*

interface NoticationApi {
    @POST("notification/add")
    suspend fun addNotification(
        @Header("Authorization") token:String,
        @Body notification: Notification
    ):Response<NoticationResponse>

    @GET("get/all/notification")
    suspend fun getallNotification(
        @Header("Authorization") token:String
    ):Response<NoticationResponse>

    @PUT("checked/notification/{id}")
    suspend fun updateChecked(
        @Header("Authorization") token:String,
        @Path ("id") id:String
    ):Response<NoticationResponse>
}