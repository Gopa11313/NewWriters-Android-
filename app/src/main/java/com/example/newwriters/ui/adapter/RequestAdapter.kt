package com.example.newwriters.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newwriters.R
import com.example.newwriters.ui.model.Request
import com.example.newwriters.ui.model.Review

class User_ShowAdapter(
        val list_Of_Request:ArrayList<Request>,
        val context: Context): RecyclerView.Adapter<User_ShowAdapter.User_ShowViewholder>(){
    class User_ShowViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val urs_name: TextView
        val request_date: TextView
        val request: TextView
        val delete_request: ImageView
        init {
            urs_name = view.findViewById(R.id.urs_name)
            request_date = view.findViewById(R.id.request_date)
            request = view.findViewById(R.id.request)
            delete_request = view.findViewById(R.id.delete_request)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): User_ShowViewholder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_show, parent, false)
        return User_ShowViewholder(view);
    }

    override fun onBindViewHolder(holder: User_ShowViewholder, position: Int) {
        val review = list_Of_Request[position]
        holder.urs_name.text=review.name
        holder.request_date.text=review.date
        holder.request.text=review.request

    }

    override fun getItemCount(): Int {
        return list_Of_Request.size
    }
}