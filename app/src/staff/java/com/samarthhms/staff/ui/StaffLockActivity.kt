package com.samarthhms.staff.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.R
import com.samarthhms.staff.constants.SchemaName
import com.samarthhms.staff.models.StaffStatus
import com.samarthhms.staff.navigator.Navigator
import com.samarthhms.staff.repository.StoredStateRepositoryImpl
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