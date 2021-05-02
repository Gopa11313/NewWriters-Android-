package com.example.newwriters.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R
import com.example.newwriters.databinding.ActivityLoginBinding
import com.example.newwriters.ui.signup.SignupActivity
class LoginActivity : AppCompatActivity() {
    private lateinit var lg_email:EditText
    private lateinit var lg_password:EditText
    private lateinit var lg_showPassword:CheckBox
    private lateinit var login:Button
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var lg_signup:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.lifecycleOwner=this
        lg_email=findViewById(R.id.lg_email)
        lg_password=findViewById(R.id.lg_password)
        lg_showPassword=findViewById(R.id.lg_showPassword)
        login=findViewById(R.id.login)
        lg_signup=findViewById(R.id.lg_signup)
        loginViewModel= ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel=loginViewModel
        lg_signup.setOnClickListener(){
            startActivity(Intent(this,SignupActivity::class.java))
        }
    }
}