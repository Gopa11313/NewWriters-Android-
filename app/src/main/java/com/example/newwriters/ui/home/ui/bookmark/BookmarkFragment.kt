package com.example.newwriters.ui.home.ui.bookmark

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.BookmarkRepository
import com.example.newwriters.ui.adapter.bookmark_Adapter
import com.example.newwriters.ui.model.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkFragment : Fragment() {

    private lateinit var viewModel: BookmarkViewModel
    private lateinit var bookmark_book: RecyclerView
    private val lst_of_Bookmarked_book=ArrayList<Bookmark>();
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java)
        val root = inflater.inflate(R.layout.bookmark_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookmark_book=view.findViewById(R.id.bookmark_book)
        getBookmarkedBook()

    }
private fun getBookmarkedBook(){
    try {
        CoroutineScope(Dispatchers.IO).launch {
            val repository=BookmarkRepository()
            val response=repository.getAllBookamarkedBook(ServiceBuilder.id!!)
            if(response.success==true){
                val data=response.data!!
                withContext(Main) {
                    val adapter = bookmark_Adapter(data as ArrayList<Bookmark>, requireContext())
                    bookmark_book.layoutManager = LinearLayoutManager(context)
                    bookmark_book.adapter = adapter;
                }
            }
            else{
                withContext(Main){
                    Toast.makeText(context, "${response.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }catch (e:Exception){
        Toast.makeText(requireContext(), "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

}
}