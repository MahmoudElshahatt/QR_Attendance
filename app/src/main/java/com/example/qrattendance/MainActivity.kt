package com.example.qrattendance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.qrattendance.database.AttendeeDataBase
import com.example.qrattendance.repository.AttendeeRepository
import com.example.qrattendance.viewModel.AttendeeViewModel
import com.example.qrattendance.viewModel.AttendeeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var splashScreen: SplashScreen
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var attendeeDataBase: AttendeeDataBase
    lateinit var attendeeViewModel: AttendeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        setContentView(R.layout.activity_main)
        setUpDataBase()
        setupNavigation()
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        bottomNavigationView = findViewById(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    private fun setUpDataBase() {
        attendeeDataBase = AttendeeDataBase.getInstance(this)
        val attendeeRepository = AttendeeRepository(
            attendeeDataBase
        )

        val viewModelProviderFactory = AttendeeViewModelFactory(
            attendeeRepository
        )

        attendeeViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[AttendeeViewModel::class.java]
    }
}
