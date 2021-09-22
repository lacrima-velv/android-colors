package com.example.colors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.colors.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val splashActivityLayout = binding.root

        setContentView(splashActivityLayout)
        // Use lifecycleScope to cancel coroutine during onStop() call
        lifecycleScope.launch {
            // Add custom delay before switching to MainActivity
            delay(2000L)

            val intent = Intent(this@SplashActivity, ColorsActivity::class.java)
            startActivity(intent)
            // Close current activity
            finish()
        }
    }
}