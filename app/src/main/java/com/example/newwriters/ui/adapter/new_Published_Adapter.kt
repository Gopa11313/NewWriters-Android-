package com.example.newwriters.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.ui.model.New_Published
import com.example.newwriters.ui.model.Top_rated

class new_Published_Adapter(
    val list_Of_NewPublished:ArrayList<New_Published>,
val context: Context): RecyclerView.Adapter<new_Published_Adapter.NEwPublished_AdapterViewholder>() {
    class NEwPublished_AdapterViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val new_published_book: ImageView
        init {
            new_published_book = view.findViewById(R.id.new_published_book)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NEwPublished_AdapterViewholder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.new_published,parent,false)
        return NEwPublished_AdapterViewholder(view);
    }

    override fun onBindViewHolder(holder:NEwPublished_AdapterViewholder, position: Int) {
        val bestseller=list_Of_NewPublished[position]
        Glide.with(context)
            .load(bestseller.image)
            .into(holder.new_published_book)
        holder.new_published_book.setOnClickListener(){
//            val intent= Intent(context,DetailActivity::class.java)
//            intent.putExtra("story",story)
//            context.startActivity(intent);
        }
    }

    override fun getItemCount(): Int {
        return list_Of_NewPublished.size
    }


}