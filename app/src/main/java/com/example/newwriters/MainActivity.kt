package com.example.newwriters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.newwriters.UI.Login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var Sologan:TextView
    private lateinit var fadein:Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Sologan=findViewById(R.id.Sologan)
        fadein=AnimationUtils.loadAnimation(this,R.anim.fadein)
        Sologan.startAnimation(fadein)
        Handler(Looper.getMainLooper()).postDelayed({
    startActivity()
        },2000)

    }
    fun startActivity(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}