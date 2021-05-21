package com.example.newwriters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.home.HomeActivity
import com.example.newwriters.ui.login.LoginActivity
import com.example.newwriters.ui.model.User
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var Sologan: TextView
    private lateinit var fadein: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Sologan = findViewById(R.id.Sologan)
        fadein = AnimationUtils.loadAnimation(this, R.anim.fadein)
        Sologan.startAnimation(fadein)
//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
//            finish()
//        }, 2000)
        val sharedPref = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        val emailPref = sharedPref.getString("email", null)
        val passwordPref = sharedPref.getString("password", "")
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            if (emailPref != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "$emailPref + ", Toast.LENGTH_SHORT).show()
                    val repository = UserRepository()
                    val user = User(email = emailPref, password = passwordPref)
                    val response = repository.LoginUSer(user)
                    if (response.success == true) {
                        ServiceBuilder.token = "Bearer ${response.token}"
                        ServiceBuilder.id = response.id
                        ServiceBuilder.night_Mode=response.night_Mode
                        delay(500)
                        startActivity(
                            Intent(this@MainActivity, HomeActivity::class.java)
                        )
                        finish()
                    }
                    else{
                        withContext(Dispatchers.Main) {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    LoginActivity::class.java
                                )
                            )
                        }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            LoginActivity::class.java
                        )
                    )
                }
                finish()
            }
        }
    }
}