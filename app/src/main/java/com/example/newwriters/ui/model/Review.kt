package com.example.newwriters.ui.model

data class Review(
        val _id:String?=null,
        val userId:String?=null,
        val bookId:String?=null,
        val ratting:Int?=null,
        val date:String?=null,
        val review:String?=null
) {
}