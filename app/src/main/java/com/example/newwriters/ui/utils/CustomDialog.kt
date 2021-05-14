package com.example.newwriters.ui.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.newwriters.R
import com.example.newwriters.api.ServiceBuilder
import com.example.newwriters.repository.ReviewRepository
import com.example.newwriters.ui.model.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomDialog:DialogFragment() {
    private lateinit var rate_book:RatingBar
    private lateinit var review_writing:EditText
    private lateinit var tv_review:TextView
    private lateinit var cancel_review:Button
    private lateinit var submit_review:Button
    var ratingFromBar:Float?=null
    var bookid:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.cornerborder);
        return inflater.inflate(R.layout.review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rate_book = view.findViewById(R.id.rate_book)
        review_writing = view.findViewById(R.id.review_writing)
        cancel_review = view.findViewById(R.id.cancel_review)
        submit_review = view.findViewById(R.id.submit_review)
        tv_review = view.findViewById(R.id.tv_review)
        rate_book.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { p0, p1, p2 ->
                ratingFromBar = p1
                Toast.makeText(requireContext(), "$p1", Toast.LENGTH_SHORT).show()
                tv_review.setText(ratingFromBar.toString())
            }
        submit_review.setOnClickListener(){
            review()
        }
        cancel_review.setOnClickListener(){
            dismiss()
        }
    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    private fun review(){
        val review=Review(userId = ServiceBuilder.id!!,bookId = ServiceBuilder.BookID!!,ratting = ratingFromBar?.toFloat().toString(),review = review_writing.text.toString())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository=ReviewRepository()
                val response=repository.addReiew(review)
                if(response.success==true)
                {
                    withContext(Main){
                        Toast.makeText(requireContext(), "${response.msg}", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(requireContext(), "${response.msg}", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}