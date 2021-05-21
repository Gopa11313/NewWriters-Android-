package com.example.newwriters.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.BookRepository
import com.example.newwriters.ui.model.BougthBooks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BouthBooksAdapter(
    val list_Of_Bougth_Book:ArrayList<BougthBooks>,
    val context: Context
): RecyclerView.Adapter<BouthBooksAdapter.BouthBooks_AdapteViewholder>(){
    class BouthBooks_AdapteViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val bougthBooks: LinearLayout
        val bought_books__cover_page: ImageView
        val bought_books_ratting: RatingBar
        val bought_books_des: TextView
        val bought_books_name: TextView
        init {
            bougthBooks = view.findViewById(R.id.bougthBooks)
            bought_books__cover_page = view.findViewById(R.id.bought_books__cover_page)
            bought_books_ratting = view.findViewById(R.id.bought_books_ratting)
            bought_books_des = view.findViewById(R.id.bought_books_des)
            bought_books_name = view.findViewById(R.id.bought_books_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BouthBooks_AdapteViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bougthbooks, parent, false)
        return BouthBooks_AdapteViewholder(view);
    }

    override fun onBindViewHolder(holder: BouthBooks_AdapteViewholder, position: Int) {
        val BougthBooks = list_Of_Bougth_Book[position]
        val bookId = BougthBooks.bookId
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = BookRepository()
                val response = repository.getBookID(bookId!!)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        holder.bought_books_name.text = response.data?.get(0)?.book_name
                        holder.bought_books_des.text = response.data?.get(0)?.description
                        val img = response.data?.get(0)?.cover_page
                        val imagePath = ServiceBuilder.loadImagePath() + img
                        if (!img.equals("noimg")) {
                            Glide.with(context)
                                .load(imagePath)
                                .into(holder.bought_books__cover_page)
                        }

                        val rat = response.data?.get(0)?.ratting
                        holder.bought_books_ratting.rating = rat!!.toFloat()
                    }
                } else {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(context, "error occurs", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } catch (e: Exception) {
            Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list_Of_Bougth_Book.size
    }
}
