package com.example.newwriters.ui.admin.home
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.newwriters.R
import com.example.newwriters.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminPanelActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view_drawer)
        val navController = findNavController(R.id.nav_host_fragment_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_book, R.id.nav_upload_book, R.id.nav_user_show,R.id.nav_request
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        toolbar.setOnMenuItemClickListener() {
            when (it.itemId) {
                R.id.admin_logout -> {
                    val builder= AlertDialog.Builder(this);
                    builder.setMessage("Do you want to logout")
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setPositiveButton("Yes"){dialogInterface,which->
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    builder.setNegativeButton("No"){
                            dialogInterface,which->
                    }
                    builder.show()
                }
            }
            true;
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.admin__panel_, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}