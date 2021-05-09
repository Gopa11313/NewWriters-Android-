package com.example.newwriters.ui.admin.home.ui.uploadbooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R
class UploadBookFragment : Fragment() {

    private lateinit var uploadBookViewModel: UploadBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uploadBookViewModel =
            ViewModelProvider(this).get(UploadBookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upload_book, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        uploadBookViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}