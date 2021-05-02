package com.example.newwriters.UI.Signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.newwriters.R
import com.example.newwriters.UI.Login.LoginActivity

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
    }
}