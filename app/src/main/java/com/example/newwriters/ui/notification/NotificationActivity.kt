package com.example.newwriters.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newwriters.R
import com.example.newwriters.repository.NotificationRepository
import com.example.newwriters.ui.adapter.NotificationAdapter
import com.example.newwriters.ui.model.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationActivity : AppCompatActivity() {
    private lateinit var notif_rcy:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        notif_rcy=findViewById(R.id.notif_rcy)
        getNotif()
    }
    private fun getNotif(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=NotificationRepository()
                val response=repository.getAllNotif()
                if(response.success==true) {
                    val data = response.data
                    if (data !== null) {
                        withContext(Main) {
                            val adapter = NotificationAdapter(
                                data as ArrayList<Notification>,
                                this@NotificationActivity
                            )
                            notif_rcy.layoutManager = LinearLayoutManager(this@NotificationActivity)
                            notif_rcy.adapter = adapter
                        }
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(this@NotificationActivity, "${response.msg}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}