package com.samarthhms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import com.samarthhms.constants.Role
import com.samarthhms.databinding.ActivityLaunchBinding
import com.samarthhms.databinding.ActivityLoginBinding
import com.samarthhms.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator : Navigator

    private val launchViewModel : LaunchViewModel by viewModels()

    private lateinit var binding: ActivityLaunchBinding

    private var isTransitionCompleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView(){
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                isTransitionCompleted = true
                launchViewModel.getLoginStatus()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })

        launchViewModel.loginStatus.observe(this){
            if(isTransitionCompleted){
                navigator.showMain(this@LaunchActivity, it)
            }
        }

    }
}