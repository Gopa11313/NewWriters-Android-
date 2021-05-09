package com.example.newwriters.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.ui.model.Bookmark
import com.example.newwriters.ui.model.Review

class bookmark_Adapter(
        val list_Of_BookMarked_Book:ArrayList<Bookmark>,
        val context: Context): RecyclerView.Adapter<bookmark_Adapter.bookmark_AdapteViewholder>(){
    class bookmark_AdapteViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val cover_page: ImageView
        val ratting: RatingBar
        val Author_name: TextView
        val book_name: TextView
        init {
            cover_page = view.findViewById(R.id.cover_page)
            ratting = view.findViewById(R.id.ratting)
            Author_name = view.findViewById(R.id.Author_name)
            book_name = view.findViewById(R.id.book_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookmark_AdapteViewholder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bookmark, parent, false)
        return bookmark_AdapteViewholder(view);
    }

    override fun onBindViewHolder(holder: bookmark_AdapteViewholder, position: Int) {
        val review = list_Of_BookMarked_Book[position]
        holder.Author_name.text=review.author_name
        holder.book_name.text=review.book_name
        holder.ratting.rating= review.ratting!!
        Glide.with(context)
                .load(review.cover_page)
                .into(holder.cover_page)
    }

    override fun getItemCount(): Int {
        return list_Of_BookMarked_Book.size
    }
}
