package com.example.newwriters.response

import com.example.newwriters.ui.model.User

data class UserResponse(
    val success:Boolean?=null,
    val token:String?=null,
    val msg:String?=null,
    val data:List<User>?=null,
    val id:String?=null,
    val role:String?=null,

) {
}