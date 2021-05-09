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
import com.example.newwriters.ui.model.Book

class BookAdapter(
        val list_Of_Book:ArrayList<Book>,
        val context: Context): RecyclerView.Adapter< BookAdapter.BookViewholder>() {
    class BookViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val boook_cover_page: ImageView
        val bkname: TextView
        val ath_name: TextView
        val rate_bar: RatingBar
        init {
            boook_cover_page = view.findViewById(R.id.boook_cover_page)
            bkname = view.findViewById(R.id.bkname)
            ath_name = view.findViewById(R.id.ath_name)
            rate_bar = view.findViewById(R.id.rate_bar)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewholder {
        val view= LayoutInflater.from(parent.context)
                .inflate(R.layout.book,parent,false)
        return BookViewholder(view);
    }

    override fun onBindViewHolder(holder: BookViewholder, position: Int) {
        val books=list_Of_Book[position]
        holder.ath_name.text=books.author_name
        holder.bkname.text=books.book_name
        holder.rate_bar.rating=books.ratting!!
        Glide.with(context)
                .load(books.cover_page)
                .into(holder.boook_cover_page)
//        holder.boook_cover_page.setOnClickListener(){
//            val intent= Intent(context, ParticularBookActivity::class.java)
//            intent.putExtra("book",bestseller)
//            context.startActivity(intent);
//        }
    }

    override fun getItemCount(): Int {
        return list_Of_Book.size
    }
}
