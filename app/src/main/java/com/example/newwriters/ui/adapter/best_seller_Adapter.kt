package com.example.newwriters.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.ui.model.Best_Seller
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.particular_book.ParticularBookActivity

class best_seller_Adapter(
        val list_Of_BEstSelller:ArrayList<Book>,
        val context: Context): RecyclerView.Adapter<best_seller_Adapter.BestSellerViewholder>() {
    class BestSellerViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val best_seller_book: ImageView
        init {
            best_seller_book = view.findViewById(R.id.best_seller_book)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewholder {
        val view= LayoutInflater.from(parent.context)
        .inflate(R.layout.bestsellers_layout,parent,false)
        return BestSellerViewholder(view)
    }

    override fun onBindViewHolder(holder: BestSellerViewholder, position: Int) {
        val bestseller=list_Of_BEstSelller[position]
        val img=bestseller.cover_page!!
        val imagePath = ServiceBuilder.loadImagePath() +img
        if (!img.equals("noimg")) {
            Glide.with(context)
                .load(imagePath)
                .into(holder.best_seller_book)
        }
        holder.best_seller_book.setOnClickListener(){
            val intent= Intent(context, ParticularBookActivity::class.java)
            intent.putExtra("_id",bestseller._id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list_Of_BEstSelller.size
    }
}
