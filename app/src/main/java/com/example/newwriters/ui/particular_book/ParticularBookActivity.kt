package com.example.newwriters.ui.particular_book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.BookRepository
import com.example.newwriters.repository.BookmarkRepository
import com.example.newwriters.repository.ReviewRepository
import com.example.newwriters.ui.adapter.review_adapter
import com.example.newwriters.ui.model.Best_Seller
import com.example.newwriters.ui.model.Bookmark
import com.example.newwriters.ui.model.Review
import com.example.newwriters.ui.utils.CustomDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class ParticularBookActivity : AppCompatActivity() {
    private lateinit var particularbook:LinearLayout
    private lateinit var user_review:RecyclerView
    private lateinit var particular_Book_img:ImageView
    private lateinit var add_to_bookMark:ImageView
    private lateinit var bkNm:TextView
    private lateinit var athName:TextView
    private lateinit var numberOfPeople:TextView
    private lateinit var writeReview:TextView
    private lateinit var description:TextView
    private lateinit var BookRatting:RatingBar
    private var Id:String?=null
//    private var book_Id:String?=null
    private val lst_of_review=ArrayList<Review>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particular_book)
        particularbook=findViewById(R.id.particularbook)
        user_review=findViewById(R.id.user_review)
        particular_Book_img=findViewById(R.id.particular_Book_img)
        bkNm=findViewById(R.id.bkNm)
        athName=findViewById(R.id.athName)
        numberOfPeople=findViewById(R.id.numberOfPeople)
        add_to_bookMark=findViewById(R.id.add_to_bookMark)
        description=findViewById(R.id.description)
        BookRatting=findViewById(R.id.BookRatting)
        writeReview=findViewById(R.id.writeReview)

        val adapter=review_adapter(lst_of_review,this)
        val mlayout=LinearLayoutManager(this)
        user_review.layoutManager= LinearLayoutManager(this)
        user_review.adapter=adapter
        getallReview()
        Id = intent.getStringExtra("_id")
        if (intent != null) {
            getBook()
        }
        writeReview.setOnClickListener (){
            ServiceBuilder.BookID=Id
            CustomDialog().show(supportFragmentManager, "MyCustomFragment")
        }
        add_to_bookMark.setOnClickListener(){
            AddToBookMArk()
        }
    }

    private fun getBook(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=BookRepository()
                val response=repository.getBookID(Id!!)
                if(response.success==true){
                    withContext(Main){
                        val img=response.data?.get(0)?.cover_page
                        val imagePath = ServiceBuilder.loadImagePath() +img
                        if (!img.equals("noimg")) {
                            Glide.with(this@ParticularBookActivity)
                                .load(imagePath)
                                .into(particular_Book_img)
                        }
                        bkNm.setText(response.data!!.get(0).book_name)
                        athName.setText(response.data!!.get(0).author_name)
                        description.setText(response.data!!.get(0).description)
                        numberOfPeople.setText(response.data!!.get(0).noofRating.toString())
                        BookRatting.rating=(response.data!!.get(0).ratting)!!.toFloat()
                    }
                }
            }

        }catch (e:Exception){
            Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getallReview(){
        try {
            CoroutineScope(Dispatchers.IO).launch{
                val repository=ReviewRepository()
                val response=repository.getallReview(Id!!)
                if(response.success==true){
                    val data=response.data
                    if(data!=null) {
                        val adapter =
                            review_adapter(data as ArrayList<Review>, this@ParticularBookActivity)
                        user_review.layoutManager = LinearLayoutManager(this@ParticularBookActivity)
                        user_review.adapter = adapter
                    }
                }
                else(
                        withContext(Main){
                            Toast.makeText(this@ParticularBookActivity, "No Review Yet.", Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }catch (e:Exception){

        }
    }
    private fun AddToBookMArk(){
        val builder= AlertDialog.Builder(this);
        builder.setMessage("Do you want to bookmark this Book.")
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Yes"){dialogInterface,which->
            val bookmark=Bookmark(userId = ServiceBuilder.id!!,bookId = Id)
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = BookmarkRepository()
                    val response = repository.BookmarkBook(bookmark)
                    if (response.success == true) {
                        withContext(Main) {
                            val snack =
                                Snackbar.make(
                                    particularbook,
                                    "${response.msg}",
                                    Snackbar.LENGTH_LONG
                                )
                            snack.show()
                        }
                    } else {
                        withContext(Main) {
                            val snack =
                                Snackbar.make(
                                    particularbook,
                                    "${response.msg}",
                                    Snackbar.LENGTH_LONG
                                )
                            snack.show()
                        }
                    }
                }
            }catch (e:Exception){
                Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("No"){
                dialogInterface,which->
        }
        builder.show()
    }
}