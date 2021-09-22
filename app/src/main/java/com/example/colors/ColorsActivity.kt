package com.example.colors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.colors.databinding.ActivityColorsBinding

class ColorsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityColorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityColorsBinding.inflate(layoutInflater)

        // Use toolbar from activity_colors.xml layout with nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        val navController = navHostFragment.navController
        val toolbar = binding.topAppBar
        /*
        The MaterialToolbar can be set as the support action bar and thus
        receive various Activity callbacks
         */
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)

        setContentView(binding.root)
    }

}