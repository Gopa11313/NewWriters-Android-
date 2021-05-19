package com.example.newwriters.ui.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

fun Activity.saveSharedPref(email:String, password:String){
    val sharedPref=getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
    val editor=sharedPref.edit()
    editor.putString("email",email)
    editor.putString("password",password)
    editor.apply()
}
fun Activity.getSharedPref() {
    val sharedPref = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
    val emailPref = sharedPref.getString("email", "")
    val passwordPref = sharedPref.getString("password", "")
}