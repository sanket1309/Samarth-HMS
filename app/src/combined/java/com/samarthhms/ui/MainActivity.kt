package com.samarthhms.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView.GONE
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.samarthhms.R
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.databinding.ActivityMainBinding
import com.samarthhms.domain.LoginStatusResponse
import com.samarthhms.domain.Status
import com.samarthhms.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    @Inject
    lateinit var navigator : Navigator

    lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

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
        navView.setNavigationItemSelectedListener(this)

        viewModel.logoutUserStatus.observe(this){
            when(it){
                Status.SUCCESS -> {
                    navigator.showMain(this, LoginStatusResponse(Role.NONE, LoggedState.LOGGED_OUT))
                }
                Status.FAILURE -> {
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout_option){
            binding.bottomNavigation.visibility = GONE
            val dialogClickListener = DialogInterface.OnClickListener{
                dialog, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.logout()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage("Are you sure, you want to logout?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show()
        }
        else if(item.itemId == R.id.dischargeCardTemplateFragment){
            binding.bottomNavigation.visibility = GONE
            supportFragmentManager.findFragmentById(R.id.nav_host)?.findNavController()?.navigate(R.id.dischargeCardTemplateFragment)
            binding.drawerLayout.closeDrawers()
        }
        else if(item.itemId == R.id.generateDischargeCardFragment){
            binding.bottomNavigation.visibility = GONE
            supportFragmentManager.findFragmentById(R.id.nav_host)?.findNavController()?.navigate(R.id.generateDischargeCardFragment)
            binding.drawerLayout.closeDrawers()
        }
        else if(item.itemId == R.id.switchAdminFragment){
            binding.bottomNavigation.visibility = GONE
            supportFragmentManager.findFragmentById(R.id.nav_host)?.findNavController()?.navigate(R.id.switchAdminFragment)
            binding.drawerLayout.closeDrawers()
        }
        else {
            val comingSoonItems = listOf(R.id.REPLACE_1,R.id.REPLACE_2,R.id.REPLACE_3,R.id.REPLACE_4,R.id.REPLACE_7,
                    R.id.REPLACE_8,R.id.REPLACE_10,R.id.REPLACE_11,R.id.REPLACE_12,R.id.REPLACE_13,R.id.REPLACE_15,R.id.REPLACE_16,R.id.REPLACE_18,R.id.REPLACE_19)
            if(item.itemId in comingSoonItems){
                binding.bottomNavigation.visibility = GONE
                supportFragmentManager.findFragmentById(R.id.nav_host)?.findNavController()?.navigate(R.id.comingSoonFragment)
                binding.drawerLayout.closeDrawers()
            }
        }
        return true
    }

    fun startProgressBar(isVisible: Boolean){
        binding.progressBar.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}