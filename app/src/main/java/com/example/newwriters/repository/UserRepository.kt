package com.example.newwriters.repository

import com.example.newwriters.api.MyApiRequest
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.api.UserApi
import com.example.newwriters.response.UserResponse
import com.example.newwriters.ui.model.User
import okhttp3.MultipartBody
import retrofit2.http.Multipart

class UserRepository:MyApiRequest() {
    val myapi=ServiceBuilder.buildServices(UserApi::class.java)

    suspend fun SignupUSer(user: User):UserResponse{
        return apiRequest {
            myapi.userSignup(user)
        }
    }
    suspend fun LoginUSer(user:User):UserResponse{
        return apiRequest {
            myapi.userLogin(user)
        }
    }
    suspend fun userByid(id:String):UserResponse{
        return apiRequest {
            myapi.UserbyId(id,ServiceBuilder.token!!)
        }
    }

    suspend fun uploaduserImage(id:String,file: MultipartBody.Part):UserResponse{
        return apiRequest {
            myapi.uploadUserImage(id,ServiceBuilder.token!!,file)
        }
    }
    suspend fun sliderCheck(id:String):UserResponse{
        return  apiRequest {
            myapi.sliderCheck(id,ServiceBuilder.token!!)
        }
    }
}