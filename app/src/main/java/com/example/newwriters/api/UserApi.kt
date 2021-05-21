package com.example.newwriters.api

import com.example.newwriters.response.UserResponse
import com.example.newwriters.ui.model.User
import okhttp3.MultipartBody
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

    @Multipart
    @PUT("upload/user/image/{id}")
    suspend fun uploadUserImage(
        @Path ("id") id:String,
        @Header("Authorization") token:String,
        @Part cover_page: MultipartBody.Part
    ):Response<UserResponse>

    @PUT("checked/slider/{id}")
    suspend fun sliderCheck(
        @Path ("id") id:String,
        @Header("Authorization") token:String,
    ):Response<UserResponse>
    @PUT("checked/nightmode/{id}")
    suspend fun nightMode(
        @Path ("id") id:String,
        @Body user: User,
        @Header("Authorization") token:String,
    ):Response<UserResponse>
}