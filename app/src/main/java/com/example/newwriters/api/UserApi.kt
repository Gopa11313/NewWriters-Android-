package com.example.newwriters.api

import com.example.newwriters.response.UserResponse
import com.example.newwriters.ui.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("user/add")
    suspend fun userSignup(@Body user:User):Response<UserResponse>

    @POST("user/login")
    suspend fun userLogin(@Body user:User):Response<UserResponse>

    @GET("user/by/{id}")
    suspend fun UserbyId(
        @Path ("id") id:String,
        @Header("Authorization") token:String
    ):Response<UserResponse>
}