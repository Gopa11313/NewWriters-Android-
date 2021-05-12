package com.example.newwriters.response

import com.example.newwriters.ui.model.Book

data class BookResponse(
    val success:Boolean?=null,
    val msg:String?=null,
    val data:MutableList<Book>?=null,
    val id:String?=null
) {
}