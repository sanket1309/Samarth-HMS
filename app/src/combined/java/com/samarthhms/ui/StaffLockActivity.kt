package com.samarthhms.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.os.BuildCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.R
import com.samarthhms.constants.SchemaName
import com.samarthhms.models.StaffStatus
import com.samarthhms.navigator.Navigator
import com.samarthhms.repository.StoredStateRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StaffLockActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var storedStateRepository: StoredStateRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_lock)

        val db = FirebaseFirestore.getInstance()
        var id: String=""
        GlobalScope.launch {
            id = storedStateRepository.getId()!!
            val reference = db.collection(SchemaName.STAFF_STATUS_COLLECTION).whereEqualTo(
                SchemaName.STAFF_ID, id)
            reference.addSnapshotListener{
                    snapshot,_ ->
                val isLocked = snapshot?.toObjects(StaffStatus::class.java)?.first()?.isLocked?: false
                if(!isLocked){
                    onUnlock()
                }
            }
        }
    }

    private fun onUnlock(){
        val intent = Intent(this, StaffMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}