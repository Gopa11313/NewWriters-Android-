package com.example.newwriters.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel:ViewModel() {
    val _name=MutableLiveData<String>()
    val _email=MutableLiveData<String>()
    val _password=MutableLiveData<String>()

    val name:LiveData<String>
        get()=_name
    val email:LiveData<String>
        get()=_email
    val pasword:LiveData<String>
        get()=_password
}