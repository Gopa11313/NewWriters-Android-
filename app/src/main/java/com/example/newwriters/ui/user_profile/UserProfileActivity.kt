package com.example.newwriters.ui.user_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.BougthBookRepository
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.adapter.BouthBooksAdapter
import com.example.newwriters.ui.model.BougthBooks
import com.example.newwriters.ui.uploadUserImage.UploadImageActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileActivity : AppCompatActivity() {
    private lateinit var user_image:CircleImageView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var user_name:TextView
    private lateinit var downloaded_books:RecyclerView
    var imageurl:String?=null
    var name:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        user_image=findViewById(R.id.user_image)
        user_name=findViewById(R.id.user_name)
        swipeRefreshLayout=findViewById(R.id.swipe)
        downloaded_books=findViewById(R.id.downloaded_books)
        getuser()
        getBooughtBooks()
        user_image.setOnClickListener(){
            startActivity(Intent(this,UploadImageActivity::class.java))
        }
        swipeRefreshLayout.setOnRefreshListener() {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=UserRepository()
                val response=repository.userByid(ServiceBuilder.id!!)
                if(response.success==true){
                    val data=response.data
                    val listdata= data?.get(0)
                    imageurl=listdata!!.image
                    withContext(Main){

                        val imagePath = ServiceBuilder.loadImagePath() +imageurl
                        if (!imageurl.equals("noimg")) {
                            Glide.with(this@UserProfileActivity)
                                .load(imagePath)
                                .into(user_image)
                        }
                    }
                }
            }
            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 2000)
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
    private fun getBooughtBooks(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val repository=BougthBookRepository()
                val response=repository.getallBougthBook(ServiceBuilder.id!!)
                if(response.success==true){
                    val data=response.data
                    if(data!==null){
                        val adapter=BouthBooksAdapter(data as ArrayList<BougthBooks>,this@UserProfileActivity)
                        downloaded_books.layoutManager = LinearLayoutManager(this@UserProfileActivity)
                        downloaded_books.adapter = adapter
                    }
                    else{
                        withContext(Main){
                            Toast.makeText(this@UserProfileActivity, "${response.msg}", Toast.LENGTH_SHORT).show()
                        }
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