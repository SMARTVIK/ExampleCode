package com.spf.panditji.view

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.spf.panditji.ApplicationDataController
import com.spf.panditji.R

class SplashActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if(location!=null){
                    ApplicationDataController.getInstance().setLastKnownLocation(location)
                }
            }
        Handler().postDelayed(Runnable {
            startActivity(Intent(this,HomeActivity::class.java))
        },2000)
    }
}
