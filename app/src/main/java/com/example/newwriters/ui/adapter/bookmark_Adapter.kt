package com.example.newwriters.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.BookRepository
import com.example.newwriters.repository.BookmarkRepository
import com.example.newwriters.ui.model.Bookmark
import com.example.newwriters.ui.model.Review
import com.example.newwriters.ui.particular_book.ParticularBookActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class bookmark_Adapter(
        val list_Of_BookMarked_Book:ArrayList<Bookmark>,
        val context: Context): RecyclerView.Adapter<bookmark_Adapter.bookmark_AdapteViewholder>(){
    class bookmark_AdapteViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val bookmarkSingleBook: LinearLayout
        val cover_page: ImageView
        val deleteBookmarked: ImageView
        val ratting: RatingBar
        val Author_name: TextView
        val book_name: TextView
        init {
            bookmarkSingleBook = view.findViewById(R.id.bookmarkSingleBook)
            cover_page = view.findViewById(R.id.cover_page)
            ratting = view.findViewById(R.id.ratting)
            Author_name = view.findViewById(R.id.Author_name)
            deleteBookmarked = view.findViewById(R.id.deleteBookmarked)
            book_name = view.findViewById(R.id.book_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookmark_AdapteViewholder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bookmark, parent, false)
        return bookmark_AdapteViewholder(view);
    }

    override fun onBindViewHolder(holder: bookmark_AdapteViewholder, position: Int) {
        val Bookmark = list_Of_BookMarked_Book[position]
        val bookId = Bookmark.bookId
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = BookRepository()
                val response = repository.getBookID(bookId!!)
                if (response.success == true) {
                    withContext(Main) {
                        holder.book_name.text = response.data?.get(0)?.book_name
                        holder.Author_name.text = response.data?.get(0)?.author_name
                        val img = response.data?.get(0)?.cover_page
                        val imagePath = ServiceBuilder.loadImagePath() + img
                        if (!img.equals("noimg")) {
                            Glide.with(context)
                                .load(imagePath)
                                .into(holder.cover_page)
                        }

                        val rat = response.data?.get(0)?.ratting
                        holder.ratting.rating = rat!!.toFloat()
                    }
                } else {
                    withContext(Main)
                    {
                        Toast.makeText(context, "error occurs", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } catch (e: Exception) {
            Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
        holder.bookmarkSingleBook.setOnClickListener() {
            val intent = Intent(context, ParticularBookActivity::class.java)
            intent.putExtra("_id", Bookmark.bookId)
            context.startActivity(intent)
        }
        holder.deleteBookmarked.setOnClickListener(){
            val builder= AlertDialog.Builder(context);
            builder.setMessage("Do you want to delete this bookmarked book.")
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                try {
                    CoroutineScope(Dispatchers.IO).launch {
                        val repository = BookmarkRepository()
                        val response = repository.DeleteBookmarkedBook(Bookmark._id!!)
                        if (response.success == true) {
                            withContext(Main) {
                                list_Of_BookMarked_Book.removeAt(position)
                                notifyDataSetChanged()
                                val snack=  Snackbar.make(it,"${response.msg}", Snackbar.LENGTH_SHORT)
                                snack.setAction("Ok") {
                                    snack.dismiss()
                                }
                                snack.show()
                            }
                        } else {
                            withContext(Main) {
                                notifyDataSetChanged()
                                val snack=  Snackbar.make(it,"${response.msg}", Snackbar.LENGTH_SHORT)
                                snack.setAction("Ok") {
                                    snack.dismiss()
                                }
                                snack.show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()

                }
            }
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return list_Of_BookMarked_Book.size
    }
}
