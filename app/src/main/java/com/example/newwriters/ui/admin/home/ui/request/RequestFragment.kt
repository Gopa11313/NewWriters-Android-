package com.example.newwriters.ui.admin.home.ui.request

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newwriters.R
import com.example.newwriters.ui.adapter.RequestAdapter
import com.example.newwriters.ui.model.Request

class RequestFragment : Fragment() {

    private lateinit var Request_show: RecyclerView
    private var lst_of_request=ArrayList<Request>()
    private lateinit var viewModel: RequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.request_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RequestViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Request_show=view.findViewById(R.id.request_show)
        getRequest()
        val adapter= RequestAdapter(lst_of_request,requireContext())
        Request_show.layoutManager = LinearLayoutManager(activity)
        Request_show.adapter=adapter
    }
    private fun getRequest(){
        lst_of_request.add(Request("Gopal Thapa","2020/05/04","Sky and Ground"))
        lst_of_request.add(Request("Binod Sharma","2020/05/04","Poor and Rich dad"))
        lst_of_request.add(Request("Aarav Mouney","2020/05/04","Sky and Ground"))
        lst_of_request.add(Request("Gopal Thapa","2020/05/04","Sky and Ground"))
    }
}