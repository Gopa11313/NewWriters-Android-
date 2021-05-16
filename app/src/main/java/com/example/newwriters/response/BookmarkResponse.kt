package com.example.newwriters.response

import com.example.newwriters.ui.model.Bookmark
import com.example.newwriters.ui.model.Review

data class BookmarkResponse(
    val success:Boolean?=null,
    val msg:String?=null,
    val data:MutableList<Bookmark>?=null,
    val id:String?=null
) {
}