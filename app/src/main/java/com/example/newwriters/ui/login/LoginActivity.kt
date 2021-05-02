package com.example.newwriters.UI.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.newwriters.R
import com.example.newwriters.UI.Signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var lg_email:EditText
    private lateinit var lg_password:EditText
    private lateinit var lg_showPassword:CheckBox
    private lateinit var login:Button
    private lateinit var lg_signup:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        lg_email=findViewById(R.id.lg_email)
        lg_password=findViewById(R.id.lg_password)
        lg_showPassword=findViewById(R.id.lg_showPassword)
        login=findViewById(R.id.login)
        lg_signup=findViewById(R.id.lg_signup)
        lg_signup.setOnClickListener(){
            startActivity(Intent(this,SignupActivity::class.java))
        }
    }
}