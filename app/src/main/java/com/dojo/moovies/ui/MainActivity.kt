package com.dojo.moovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dojo.moovies.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        supportActionBar?.hide()
        loadGoogleAds()

    }

    private fun loadGoogleAds() {
        val request = AdRequest.Builder()
            .build()

        val testDevices = listOf(AdRequest.DEVICE_ID_EMULATOR)

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build()
        )
        
        binding.adMainActivity.loadAd(request)
    }


}