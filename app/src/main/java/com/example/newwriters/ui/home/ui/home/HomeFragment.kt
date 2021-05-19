package com.example.newwriters.ui.home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newwriters.R
import com.example.newwriters.repository.BookRepository
import com.example.newwriters.ui.adapter.best_seller_Adapter
import com.example.newwriters.ui.adapter.new_Published_Adapter
import com.example.newwriters.ui.adapter.top_ratted_Adapter
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.notification.NotificationActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bst_seller: RecyclerView
    private lateinit var top_rated: RecyclerView
    private lateinit var new_publised: RecyclerView
    private lateinit var notification: ImageButton
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bst_seller=view.findViewById(R.id.bst_seller_rcy)
        top_rated=view.findViewById(R.id.top_rated_rcy)
        new_publised=view.findViewById(R.id.new_publised_rcy)
        notification=view.findViewById(R.id.notification)

//    ----best selller boook apater---//
        book_Best_Seller()
//
////        ----top rated books-----//
        topratted()
//        //----new published books----/
//        Handler(Looper.getMainLooper()).postDelayed({
            New_Published()
//        },1000)
        notification.setOnClickListener(){
        startActivity(Intent(context,NotificationActivity::class.java))
        }
    }


    private fun book_Best_Seller(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val repository=BookRepository()
                val res=repository.topSoldBook()
                if(res.success==true){
                    val lstBook_top_sold = res.data
                    withContext(Main) {
                        val top_ratted = context?.let {
                            best_seller_Adapter(
                                lstBook_top_sold as ArrayList<Book>,
                                it
                            )
                        }
                        val NewLayoutManager = LinearLayoutManager(requireContext())
                        NewLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                        bst_seller.layoutManager = NewLayoutManager
                        bst_seller.adapter = top_ratted;
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
   }


    private fun topratted(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val repository=BookRepository()
                val res=repository.AllTopRatedBook()
                if(res.success==true){
                    val lstBook_top_ratted = res.data
                    withContext(Main) {
                        val top_ratted = context?.let {
                            top_ratted_Adapter(
                                lstBook_top_ratted as ArrayList<Book>,
                                it
                            )
                        }
                        val NewLayoutManager = LinearLayoutManager(requireContext())
                        NewLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                        top_rated.layoutManager = NewLayoutManager
                        top_rated.adapter = top_ratted;
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
      }
    private fun New_Published(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=BookRepository()
                val response=repository.getAllBook()
                if(response.success==true){
                        val lstBook_New_Published = response.data
                    withContext(Main) {
                        val New_Published = context?.let {
                            new_Published_Adapter(
                                lstBook_New_Published as ArrayList<Book>,
                                it
                            )
                        }
                        val NewLayoutManager = LinearLayoutManager(requireContext())
                        NewLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                        new_publised.layoutManager = NewLayoutManager
                        new_publised.adapter = New_Published;
                    }
               }
               else{
                   withContext(Main){
                       Toast.makeText(requireContext(), "error here", Toast.LENGTH_SHORT).show()
                   }
               }
           }
       }catch (e:Exception){
           Toast.makeText(requireContext(), "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
       }
     }
}