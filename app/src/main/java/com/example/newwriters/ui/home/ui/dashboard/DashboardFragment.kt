package com.example.newwriters.ui.home.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R
import com.example.newwriters.ui.adapter.dashboradIteamAdapter
import com.example.newwriters.ui.model.DashBoradIteam


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var listView: ListView
    var adapter: dashboradIteamAdapter? = null
    private val DashBoradIteamAdpater: ArrayList<DashBoradIteam> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView=view.findViewById(R.id.listView)
        DashBoradIteamAdpater.add(DashBoradIteam("Profile",
            R.drawable.ic_baseline_person_outline_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Upload Books",
            R.drawable.ic_baseline_person_outline_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Info",
            R.drawable.ic_baseline_info_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Help",
            R.drawable.ic_baseline_help_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Logout",
            R.drawable.ic_baseline_logout_24))

        adapter = context?.let { dashboradIteamAdapter(it, DashBoradIteamAdpater) }
        listView.adapter = adapter
    }
}