package com.example.newwriters.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.particular_book.ParticularBookActivity

class top_ratted_Adapter (
    val list_Of_topRated:ArrayList<Book>,
        val context: Context): RecyclerView.Adapter<top_ratted_Adapter.TopRattedViewholder>() {
    class TopRattedViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val top_rated_book: ImageView
        init {
            top_rated_book = view.findViewById(R.id.top_rated_book)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRattedViewholder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.top_rated_layout,parent,false)
        return TopRattedViewholder(view);
    }

    override fun onBindViewHolder(holder:TopRattedViewholder, position: Int) {
        val top_ratted=list_Of_topRated[position]
        val img=top_ratted.cover_page!!
        val imagePath = ServiceBuilder.loadImagePath() +img
        if (!img.equals("noimg")) {
            Glide.with(context)
                .load(imagePath)
                .into(holder.top_rated_book)
        }
        holder.top_rated_book.setOnClickListener(){
            val intent= Intent(context, ParticularBookActivity::class.java)
            intent.putExtra("_id",top_ratted._id)
            context.startActivity(intent);
        }
    }

    override fun getItemCount(): Int {
        return list_Of_topRated.size
    }
}
