package com.example.newwriters.api

import com.example.newwriters.response.UserResponse
import com.example.newwriters.ui.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("user/add")
    suspend fun userSignup(@Body user:User):Response<UserResponse>
}