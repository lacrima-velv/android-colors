package com.example.colors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val mainActivityLayout = binding.root
        val listOfColorsAdapter = ColorsAdapter(this, mainActivityLayout)
        binding.listOfColors.adapter = listOfColorsAdapter

        // Change Theme to Default form Splash Screen Theme
        setTheme(R.style.Theme_Colors)
        setContentView(mainActivityLayout)
    }

}