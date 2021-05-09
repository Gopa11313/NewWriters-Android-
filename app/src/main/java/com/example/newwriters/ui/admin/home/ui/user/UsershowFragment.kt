package com.example.newwriters.ui.admin.home.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R

class UsershowFragment : Fragment() {

    private lateinit var usershowViewModel: UsershowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usershowViewModel =
            ViewModelProvider(this).get(UsershowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_show, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        usershowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}