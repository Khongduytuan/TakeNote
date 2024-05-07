package com.eagletech.takenote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eagletech.takenote.databinding.ActivityMainBinding
import com.eagletech.takenote.sharepref.SharedPreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)
        if (sharedPreferencesManager.getLives() == 0){
           val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
            finish()
        }

        setSupportActionBar(binding.toolbar)
    }
}