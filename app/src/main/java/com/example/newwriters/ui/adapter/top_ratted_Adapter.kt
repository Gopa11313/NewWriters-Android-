package com.example.newwriters.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.ui.model.Top_rated

class top_ratted_Adapter (
    val list_Of_topRated:ArrayList<Top_rated>,
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
        val bestseller=list_Of_topRated[position]
        Glide.with(context)
            .load(bestseller.image)
            .into(holder.top_rated_book)
        holder.top_rated_book.setOnClickListener(){
//            val intent= Intent(context,DetailActivity::class.java)
//            intent.putExtra("story",story)
//            context.startActivity(intent);
        }
    }

    override fun getItemCount(): Int {
        return list_Of_topRated.size
    }
}
