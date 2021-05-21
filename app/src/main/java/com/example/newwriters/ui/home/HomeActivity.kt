package com.example.newwriters.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nightmode()
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view_drawer)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_bookmark))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    @SuppressLint("ResourceType")
    fun nightmode(){
        if(ServiceBuilder.night_Mode==true){
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        }
        else{
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }
    }
}