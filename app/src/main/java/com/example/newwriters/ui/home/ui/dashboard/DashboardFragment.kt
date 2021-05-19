package com.example.newwriters.ui.home.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R
import com.example.newwriters.ui.adapter.dashboradIteamAdapter
import com.example.newwriters.ui.login.LoginActivity
import com.example.newwriters.ui.model.DashBoradIteam
import com.example.newwriters.ui.setting.SettingActivity
import com.example.newwriters.ui.upload_books.UploadBookActivity
import com.example.newwriters.ui.user_profile.UserProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

        DashBoradIteamAdpater.add(DashBoradIteam("Info",
            R.drawable.ic_baseline_info_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Help",
            R.drawable.ic_baseline_help_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Setting",
            R.drawable.ic_baseline_settings_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Logout",
            R.drawable.ic_baseline_logout_24))

        adapter = context?.let { dashboradIteamAdapter(it, DashBoradIteamAdpater) }
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemText = parent.getItemAtPosition(position)
                if(selectedItemText==0){
                    startActivity(Intent(context  ,UserProfileActivity::class.java))
                }
                else if(selectedItemText==4){
                logout()
            }
                else if( selectedItemText==3){
                    startActivity(Intent(context  ,SettingActivity::class.java))
                }
            }
    }
    fun logout(){
        val builder= AlertDialog.Builder(requireContext());
        builder.setMessage("Do you want to logout")
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Yes"){dialogInterface,which->
            val sharedPref =requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
            val editor=sharedPref.edit()
            editor.remove("email")
            editor.remove("password")
                .apply()
            CoroutineScope(Dispatchers.IO).launch{
                withContext(Dispatchers.Main){
                    startActivity(Intent(context, LoginActivity::class.java))
                }
            }
        }
        builder.setNegativeButton("No"){
                dialogInterface,which->
        }
        builder.show()
    }
}