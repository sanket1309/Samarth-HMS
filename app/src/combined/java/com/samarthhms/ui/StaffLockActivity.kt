package com.samarthhms.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.samarthhms.R
import com.samarthhms.constants.LoggedState
import com.samarthhms.constants.Role
import com.samarthhms.databinding.ActivityStaffLockBinding
import com.samarthhms.domain.LoginStatusResponse
import com.samarthhms.domain.Status
import com.samarthhms.navigator.Navigator
import com.samarthhms.repository.StoredStateRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StaffLockActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var storedStateRepository: StoredStateRepositoryImpl

    lateinit var binding: ActivityStaffLockBinding

    private val viewModel: StaffLockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffLockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        val toolbar = binding.materialToolbar
        toolbar.setTitleTextColor(resources.getColor(R.color.white, null))
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_staff) as NavHostFragment
        val navController = navHostFragment.navController

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

        viewModel.isLocked.observe(this){
            if(!it){
                onUnlock()
            }
        }
    }

    private fun onUnlock(){
        val intent = Intent(this, StaffMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_option) {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
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
            return true
        }
        return true
    }

    fun startProgressBar(isVisible: Boolean){
        binding.progressBar.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}