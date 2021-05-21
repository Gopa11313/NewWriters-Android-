package com.example.newwriters.ui.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.adapter.top_ratted_Adapter
import com.example.newwriters.ui.home.HomeActivity
import com.example.newwriters.ui.login.LoginActivity
import com.example.newwriters.ui.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception


class SettingActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switch1:Switch
    var flag:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        switch1=findViewById(R.id.switch1)
        switch1.isChecked = ServiceBuilder.night_Mode==true

        switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val user=User(night_Mode = true)
                flag=true
                ServiceBuilder.night_Mode==true
                nightmode(user)
//                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            } else {
                val user=User(night_Mode = false)
                nightmode(user)
                ServiceBuilder.night_Mode=false
//                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            }
        })
    }
    private fun nightmode(user: User){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=UserRepository()
                val response=repository.nightMode(ServiceBuilder.id!!,user)
                if(response.success==true){
                    withContext(Main){
                        val builder= AlertDialog.Builder(this@SettingActivity);
                        builder.setMessage("The Application will start from home page. Are you sure!!")
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setPositiveButton("Yes"){dialogInterface,which->
                            if(flag==true){
                                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                            }
                            else{
                                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                            }
                            startActivity(Intent(this@SettingActivity,HomeActivity::class.java))
                            finish()
                        }
                        builder.setNegativeButton("No"){
                                dialogInterface,which->
                        }
                        builder.show()

                    }
                }
                else{
                    Toast.makeText(this@SettingActivity, "sorry you cann't enable the night mode ", Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e:Exception){
            Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}