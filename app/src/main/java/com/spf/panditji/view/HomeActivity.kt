package com.spf.panditji.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
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

import com.spf.panditji.util.Constants
import ir.apend.slider.model.Slide
import kotlinx.android.synthetic.main.activity_home.*
import java.util.ArrayList
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class HomeActivity : AppCompatActivity() {

    private var appBarConfiguration : AppBarConfiguration ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.spf.panditji.R.layout.activity_home)
        setUpDrawerLayout()

    }

    private fun setUpDrawerLayout() {
        val toolBar = findViewById<Toolbar>(com.spf.panditji.R.id.toolbar);
        setSupportActionBar(toolBar)

        val drawerLayout = findViewById<DrawerLayout>(com.spf.panditji.R.id.drawer_layout)

        val navView = findViewById<NavigationView>(com.spf.panditji.R.id.nav_view)

        val navigationController = findNavController(com.spf.panditji.R.id.nav_host_fragment)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                com.spf.panditji.R.id.nav_home,
                com.spf.panditji.R.id.nav_categories,
                com.spf.panditji.R.id.nav_my_bookings,
                com.spf.panditji.R.id.nav_my_account,
                com.spf.panditji.R.id.nav_about_us,
                com.spf.panditji.R.id.nav_logout
            ), drawerLayout
        )



        setupActionBarWithNavController(navigationController, appBarConfiguration!!)
        navView.setupWithNavController(navigationController)

        var view : View = navView.getHeaderView(0);

        if(!ApplicationDataController.getInstance().isUserLoggedIn){
            view.findViewById<View>(com.spf.panditji.R.id.profile).visibility = View.GONE
            view.findViewById<View>(com.spf.panditji.R.id.go_to_sign_in).visibility = View.VISIBLE
        }else{
            view.findViewById<View>(com.spf.panditji.R.id.profile).visibility = View.VISIBLE
            view.findViewById<View>(com.spf.panditji.R.id.go_to_sign_in).visibility = View.GONE
            var nameView : TextView = view.findViewById(com.spf.panditji.R.id.name)
            var emailView : TextView = view.findViewById(com.spf.panditji.R.id.email)
            var userProfileModel = ApplicationDataController.getInstance().currentUserProfile
            nameView.text = userProfileModel?.name
            emailView.text = userProfileModel?.email
        }

        view.findViewById<View>(com.spf.panditji.R.id.go_to_sign_in).setOnClickListener {
            finish()
            startActivity(Intent(this@HomeActivity,SignInActivity::class.java))
        }


        if (intent.hasExtra(Constants.OPEN_BOOKING)) {
            navView.getMenu().getItem(2).setChecked(true);
            navigationController.navigate(com.spf.panditji.R.id.nav_my_bookings)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.spf.panditji.R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.getItemId() == android.R.id.home){ // use android.R.id
            drawer_layout.openDrawer(Gravity.LEFT);
            return true
        }

        return super.onOptionsItemSelected(item)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(com.spf.panditji.R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration!!) || super.onSupportNavigateUp()
    }

}
