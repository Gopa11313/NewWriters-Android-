package com.example.newwriters.ui.admin.home.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newwriters.R
import com.example.newwriters.repository.BookRepository
import com.example.newwriters.ui.adapter.BookAdapter
import com.example.newwriters.ui.adapter.bookmark_Adapter
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookFragment : Fragment() {
    private lateinit var bookViewModel: BookViewModel
    private lateinit var recyclar_for_book: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookViewModel =
            ViewModelProvider(this).get(BookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_drawer_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        bookViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclar_for_book=view.findViewById(R.id.recyclar_for_book)
        getvalue()

    }
    private fun getvalue(){
       try {
           CoroutineScope(Dispatchers.IO).launch {
               val repository=BookRepository()
               val response=repository.getAllBook()
               if(response.success==true){
                   val lst_of_book=response.data
                   val adapter= BookAdapter(lst_of_book as ArrayList<Book>,requireContext())
                   recyclar_for_book.layoutManager = LinearLayoutManager(activity)
                   recyclar_for_book.adapter=adapter;
               }
               else{
                   withContext(Main){
                       Toast.makeText(requireContext(), "${response.msg}", Toast.LENGTH_SHORT).show()
                   }
               }
           }
       }catch (e:Exception){
           Toast.makeText(requireContext(), "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
       }
    }
}