package com.dev.appointments.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dev.appointments.R
import com.dev.appointments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        enableEdgeToEdge()

        binding.btnGetstarted.setOnClickListener {
            startActivity(HomeActivity.newIntent(this))
            finish()
        }
    }
}