package com.example.newwriters.ui.particular_book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newwriters.R
import com.example.newwriters.ui.adapter.review_adapter
import com.example.newwriters.ui.model.Best_Seller
import com.example.newwriters.ui.model.Review

class ParticularBookActivity : AppCompatActivity() {
    private lateinit var user_review:RecyclerView
    private val lst_of_review=ArrayList<Review>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particular_book)
        user_review=findViewById(R.id.user_review)
        value()
        val adapter=review_adapter(lst_of_review,this)
        val mlayout=LinearLayoutManager(this)
        user_review.layoutManager= LinearLayoutManager(this)
        user_review.adapter=adapter

        val intent = intent.getStringExtra("_id")
        if (intent != null) {

        }
    }

    private fun value(){
    }
}