package com.example.newwriters.ui.login

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R
import com.example.newwriters.SliderActivity
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.databinding.ActivityLoginBinding
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.admin.home.AdminPanelActivity
import com.example.newwriters.ui.model.User
import com.example.newwriters.ui.signup.SignupActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var login_layout:LinearLayout
    private lateinit var lg_email:EditText
    private lateinit var lg_password:EditText
    private lateinit var lg_showPassword:CheckBox
    private lateinit var login:Button
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var lg_signup:Button
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.lifecycleOwner=this
        login_layout=findViewById(R.id.login_layout)
        lg_email=findViewById(R.id.lg_email)
        lg_password=findViewById(R.id.lg_password)
        lg_showPassword=findViewById(R.id.lg_showPassword)
        login=findViewById(R.id.login)
        lg_signup=findViewById(R.id.lg_signup)
        loginViewModel= ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel=loginViewModel
        if (!hasPermission()) {
            requestPermission()
        }
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
                        ServiceBuilder.token="Bearer ${response.token}"
                        ServiceBuilder.id=response._id
                        withContext(Main) {
                            startActivity(Intent(this@LoginActivity, AdminPanelActivity::class.java))
                            finish()
                        }
                    }
                    else{
                    withContext(Main) {
                        startActivity(Intent(this@LoginActivity, SliderActivity::class.java))
                        finish()
                    }
                }
                }
                else{
                    withContext(Main){
                        val  snack=
                            Snackbar.make(login_layout, "Invalid Credential!!", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@LoginActivity,
            permissions, 876
        )
    }
    fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }
}