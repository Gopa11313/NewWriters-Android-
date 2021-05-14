package com.example.newwriters.response

import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.Review

class ReviewResponse (
    val success:Boolean?=null,
    val msg:String?=null,
    val data:MutableList<Review>?=null,
    val id:String?=null
){
}