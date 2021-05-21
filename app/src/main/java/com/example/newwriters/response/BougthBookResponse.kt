package com.example.newwriters.response

import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.BougthBooks

class BougthBookResponse(val success:Boolean?=null,
                         val msg:String?=null,
                         val data:MutableList<BougthBooks>?=null,
                         val id:String?=null) {
}