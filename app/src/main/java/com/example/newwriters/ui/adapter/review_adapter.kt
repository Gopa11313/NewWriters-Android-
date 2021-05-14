package com.example.newwriters.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.model.Review
import com.example.newwriters.ui.particular_book.ParticularBookActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class review_adapter(
        val list_Of_Review:ArrayList<Review>,
        val context: Context): RecyclerView.Adapter<review_adapter.review_adapterViewholder>(){
    class review_adapterViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val user_name: TextView
        val review_ratting: RatingBar
        val date: TextView
        val review: TextView
        init {
            user_name = view.findViewById(R.id.user_name)
            review_ratting = view.findViewById(R.id.review_ratting)
            date = view.findViewById(R.id.date)
            review = view.findViewById(R.id.review)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): review_adapterViewholder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_review, parent, false)
        return review_adapterViewholder(view);
    }

    override fun onBindViewHolder(holder: review_adapterViewholder, position: Int) {
        val review = list_Of_Review[position]
        holder.date.text=review.date
        holder.review.text=review.review
        val userid=review.userId
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=UserRepository()
                val response=repository.userByid(userid!!)
                if(response.success==true){
                    withContext(Main){
                        holder.user_name.setText(response.data?.get(0)?.name)
                    }
                }
            else{
                withContext(Main){
                    Toast.makeText(context, "${response.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    catch (e:Exception){
        Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
        }

    override fun getItemCount(): Int {
        return list_Of_Review.size
    }
}