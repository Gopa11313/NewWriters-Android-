package com.example.newwriters.ui.user_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.uploadUserImage.UploadImageActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileActivity : AppCompatActivity() {
    private lateinit var user_image:CircleImageView
    private lateinit var user_name:TextView
    var imageurl:String?=null
    var name:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        user_image=findViewById(R.id.user_image)
        user_name=findViewById(R.id.user_name)
        getuser()

        user_image.setOnClickListener(){
            startActivity(Intent(this,UploadImageActivity::class.java))
        }



    }
    private fun getuser(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
               val repository=UserRepository()
               val response=repository.userByid(ServiceBuilder.id!!)
                if(response.success==true) {
                    imageurl = response.data?.get(0)?.image
                    name = response.data?.get(0)?.name
                        withContext(Main){
                            val imagePath = ServiceBuilder.loadImagePath() +imageurl
                            if (!imageurl.equals("noimg")) {
                                Glide.with(this@UserProfileActivity)
                                    .load(imagePath)
                                    .into(user_image)
                            }
                            user_name.setText(name)
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(this@UserProfileActivity, "${response.msg}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}