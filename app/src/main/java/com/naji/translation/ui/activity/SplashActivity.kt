package com.naji.translation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.naji.translation.databinding.ActivitySplashBinding
import com.naji.translation.view_model.activty.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeListOfLanguagesLiveData()
    }

    private fun observeListOfLanguagesLiveData() {
        viewModel.listOfLanguagesLiveData.observe(this) { languages ->
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).apply { putExtra("languages", languages) }
            )
            finish()
        }
    }
}