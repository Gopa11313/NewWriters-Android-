package com.example.newwriters.repository

import com.example.newwriters.api.MyApiRequest
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.api.UserApi
import com.example.newwriters.response.UserResponse
import com.example.newwriters.ui.model.User

class UserRepository:MyApiRequest() {
    val myapi=ServiceBuilder.buildServices(UserApi::class.java)

    suspend fun SignupUSer(user: User):UserResponse{
        return apiRequest {
            myapi.userSignup(user)
        }
    }
}