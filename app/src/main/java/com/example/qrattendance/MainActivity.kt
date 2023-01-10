package com.example.qrattendance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var splashScreen: SplashScreen
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        bottomNavigationView = findViewById(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

}