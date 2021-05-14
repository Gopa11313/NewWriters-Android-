package com.example.newwriters.ui.model

import android.os.Parcel
import android.os.Parcelable

data class Book(
        val _id:String?=null,
        val book_name:String?=null,
        val cover_page: String?=null,
        val book: String?=null,
        val ratting:Float?=null,
        val noofRating:Int?=null,
        val author_name:String?=null,
        val description:String?=null,
        val price:String?=null,
        val offer:String?=null
):Parcelable
{
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Float::class.java.classLoader) as? Float,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
        ) {
        }

        override fun describeContents(): Int {
                TODO("Not yet implemented")
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                TODO("Not yet implemented")
        }

        companion object CREATOR : Parcelable.Creator<Book> {
                override fun createFromParcel(parcel: Parcel): Book {
                        return Book(parcel)
                }

                override fun newArray(size: Int): Array<Book?> {
                        return arrayOfNulls(size)
                }
        }
}