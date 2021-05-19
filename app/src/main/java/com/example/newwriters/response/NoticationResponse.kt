package com.example.newwriters.response

import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.Notification

data class NoticationResponse(val success:Boolean?=null,
                              val msg:String?=null,
                              val data:MutableList<Notification>?=null,
                              val id:String?=null) {
}