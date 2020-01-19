package com.spf.panditji.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import androidx.annotation.IntegerRes
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.spf.panditji.ApplicationDataController
import com.spf.panditji.R
import ir.apend.slider.model.Slide
import java.util.ArrayList

class HomeActivity : AppCompatActivity() {

    private var appBarConfiguration : AppBarConfiguration ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpDrawerLayout()

    }

    private fun setUpDrawerLayout() {
        val toolBar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolBar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navView = findViewById<NavigationView>(R.id.nav_view)

        val navigationController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home,R.id.nav_categories,R.id.nav_my_bookings,R.id.nav_my_account,R.id.nav_about_us),drawerLayout
        )

        setupActionBarWithNavController(navigationController, appBarConfiguration!!)
        navView.setupWithNavController(navigationController)

        var view : View = navView.getHeaderView(0);

        if(!ApplicationDataController.getInstance().isUserLoggedIn){
            view.findViewById<View>(R.id.profile).visibility = View.GONE
            view.findViewById<View>(R.id.go_to_sign_in).visibility = View.VISIBLE
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration!!) || super.onSupportNavigateUp()
    }

}
