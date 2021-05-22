package com.example.newwriters.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.BookRepository
import com.example.newwriters.repository.NotificationRepository
import com.example.newwriters.ui.model.Notification
import com.example.newwriters.ui.particular_book.ParticularBookActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationAdapter (
    val list_Of_Notification:ArrayList<Notification>,
    val context: Context): RecyclerView.Adapter<NotificationAdapter.notication_AdapterViewholder>() {
    class notication_AdapterViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val openBookFromNotification:LinearLayout
        val notif_cover_page: CircleImageView
        val notif_description: TextView
        val notifdes: TextView
        init {
            openBookFromNotification = view.findViewById(R.id.openBookFromNotification)
            notif_cover_page = view.findViewById(R.id.notif_cover_page)
            notif_description = view.findViewById(R.id.notif_description)
            notifdes = view.findViewById(R.id.notifdes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notication_AdapterViewholder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.notification,parent,false)
        return notication_AdapterViewholder(view);
    }

    override fun onBindViewHolder(holder:notication_AdapterViewholder, position: Int) {
        val Notification=list_Of_Notification[position]
        val bookid=Notification.bookId
        val checked=Notification.checked
        if(checked!!.equals(true)){
            holder.openBookFromNotification.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        CoroutineScope(Dispatchers.IO).launch{
            val repository=BookRepository()
            val response=repository.getBookID(bookid!!)
            if(response.success==true){
                withContext(Main){
                    val img=response.data?.get(0)?.cover_page!!
                    val des=response.data?.get(0)?.description
                    val imagePath = ServiceBuilder.loadImagePath() +img
                    if (!img.equals("noimg")) {
                        Glide.with(context)
                            .load(imagePath)
                            .into(holder.notif_cover_page)
                    }
                    val ath_name=response.data.get(0).author_name
                    val bookname=response.data.get(0).book_name
                    holder.notif_description.setText("$ath_name uploaded $bookname book.")
                    holder.notifdes.setText(des)
                }
            }
        }
        holder.openBookFromNotification.setOnClickListener() {
            if (checked!!.equals(true)) {
                    val intent = Intent(context, ParticularBookActivity::class.java)
                    intent.putExtra("_id", bookid!!)
                    context.startActivity(intent)
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = NotificationRepository()
                    val response = repository.checked(Notification._id!!)
                    if (response.success == true) {
                        withContext(Main) {
                            val intent = Intent(context, ParticularBookActivity::class.java)
                            intent.putExtra("_id", bookid)
                            context.startActivity(intent)
                        }
                    } else {
                        withContext(Main) {
                            Toast.makeText(context, "${response.msg}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list_Of_Notification.size
    }


}