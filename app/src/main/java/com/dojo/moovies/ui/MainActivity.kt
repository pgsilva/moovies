package com.dojo.moovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dojo.moovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        supportActionBar?.hide()

    }


}