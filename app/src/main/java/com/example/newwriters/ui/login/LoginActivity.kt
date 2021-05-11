package com.example.newwriters.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R
import com.example.newwriters.SliderActivity
import com.example.newwriters.databinding.ActivityLoginBinding
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.admin.home.AdminPanelActivity
import com.example.newwriters.ui.model.User
import com.example.newwriters.ui.signup.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

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
        login.setOnClickListener(){
           login()
        }
    }
    private fun login(){
        val user=User(email = lg_email.text.toString(),password = lg_password.text.toString())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=UserRepository()
                val response=repository.LoginUSer(user)
                if(response.success==true){
                    if(response.role=="Admin"){
                        withContext(Main) {
                            startActivity(Intent(this@LoginActivity, AdminPanelActivity::class.java))
                        }
                    }
                    withContext(Main) {
                        startActivity(Intent(this@LoginActivity, SliderActivity::class.java))
                    }

                }
            }
        }catch (e:Exception){
            Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}