package com.samarthhms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.samarthhms.R
import com.samarthhms.databinding.ActivityLoginBinding
import com.samarthhms.databinding.ActivityMainBinding
import com.samarthhms.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator : Navigator

    private lateinit var binding: ActivityMainBinding

    lateinit var data: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        val toolbar = binding.materialToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setTitleTextColor(resources.getColor(R.color.white, null))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)

        val builder = AppBarConfiguration.Builder(navController.graph)
        val drawer = binding.drawerLayout
        builder.setOpenableLayout(drawer)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

        val navView = binding.navigationView
        NavigationUI.setupWithNavController(navView, navController)
    }
}