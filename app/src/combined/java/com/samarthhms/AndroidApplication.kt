package com.samarthhms

import android.app.Application
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.samarthhms.constants.SchemaName
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e -> sendLogsToFirebase(t,e) }
    }

    private fun sendLogsToFirebase(t: Thread, e: Throwable){
        val db = FirebaseFirestore.getInstance()
        val reference = db.collection("CrashLogs")
        val document = reference.document("log-item")
        document.set(e)
        throw e
    }
}