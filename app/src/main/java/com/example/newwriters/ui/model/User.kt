package com.example.newwriters.ui.model

data class User(
    val _id:String?=null,
    val name:String?=null,
    val email:String?=null,
    val password:String?=null,
    val image:String?=null,
    val slider:Boolean?=null,
    val night_Mode:Boolean?=null,
    val role:String?=null
) {
}