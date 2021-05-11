package com.example.newwriters.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.newwriters.R
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.login.LoginActivity
import com.example.newwriters.ui.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var sg_name:EditText
    private lateinit var sg_email:EditText
    private lateinit var sg_password:EditText
    private lateinit var sg_showPassword:CheckBox
    private lateinit var signup:Button
    private lateinit var sg_signin:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        sg_name=findViewById(R.id.sg_name)
        sg_email=findViewById(R.id.sg_email)
        sg_password=findViewById(R.id.sg_password)
        signup=findViewById(R.id.signup)
        sg_showPassword=findViewById(R.id.sg_showPassword)
        sg_signin=findViewById(R.id.sg_signin)
        sg_signin.setOnClickListener(){
            startActivity(Intent(this,LoginActivity::class.java))
        }
        signup.setOnClickListener(){
            userSignUp()
        }
    }
    private fun userSignUp(){
        val user= User(name = sg_name.text.toString(),email = sg_email.text.toString(),password = sg_password.text.toString())
        CoroutineScope(Dispatchers.IO).launch {
            val repository=UserRepository()
            val response=repository.SignupUSer(user)
            if(response.success==true){
                withContext(Main){
                    Toast.makeText(this@SignupActivity, "User Register Successfully!!", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                withContext(Main){
                    Toast.makeText(this@SignupActivity, response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}