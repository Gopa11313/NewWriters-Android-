package com.example.newwriters.ui.model

import android.os.Parcel
import android.os.Parcelable

data class Best_Seller(
       val image:String?=null
):Parcelable {
       constructor(parcel: Parcel) : this(parcel.readString()) {
       }

       override fun writeToParcel(parcel: Parcel, flags: Int) {
              parcel.writeString(image)
       }

       override fun describeContents(): Int {
              return 0
       }

       companion object CREATOR : Parcelable.Creator<Best_Seller> {
              override fun createFromParcel(parcel: Parcel): Best_Seller {
                     return Best_Seller(parcel)
              }

              override fun newArray(size: Int): Array<Best_Seller?> {
                     return arrayOfNulls(size)
              }
       }
}