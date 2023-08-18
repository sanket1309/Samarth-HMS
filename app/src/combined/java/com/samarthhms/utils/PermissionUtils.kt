package com.samarthhms.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.samarthhms.Manifest
import com.samarthhms.constants.Constants
import java.time.Duration
import java.util.Objects

class PermissionUtils {
    companion object{
        fun checkPermissions(context: Context,permissions: List<String> = Constants.Permissons.PERMISSION_LIST): List<String>{
            val permissionsToRequest = permissions.filter {
                PackageManager.PERMISSION_GRANTED != (context.checkSelfPermission(it))
            }
            return permissionsToRequest
        }

        fun arePermissionsAllowed(context: Context,permissions: List<String> = Constants.Permissons.PERMISSION_LIST): Boolean{
            val permissionsToRequest = checkPermissions(context, permissions)
            return permissionsToRequest.isEmpty()
        }

        fun requestReadWritePermissions(activity: Activity,permissions: List<String> = Constants.Permissons.PERMISSION_LIST) {
            if(arePermissionsAllowed(activity, permissions)){
                return
            }
            ActivityCompat.requestPermissions(
                activity,
                Constants.Permissons.PERMISSION_LIST.toTypedArray(),
                Constants.Permissons.REQUEST_CODE_READ_WRITE
            )
        }

        private fun requestPermissions(requestCode: Int, activity: Activity,permissions: List<String> = Constants.Permissons.PERMISSION_LIST) {
            if(arePermissionsAllowed(activity, permissions)){
                return
            }
            ActivityCompat.requestPermissions(
                activity,
                permissions.toTypedArray(),
                requestCode
            )
        }

        fun onReadWritePermissionResults(requestCode: Int, grantResults: IntArray, onGrantedPermission: (()->Unit)?=null, onDeniedPermission: (()->Unit)?=null) {
            if(requestCode == Constants.Permissons.REQUEST_CODE_READ_WRITE){
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onGrantedPermission?.let { onGrantedPermission() }
                } else {
                    onDeniedPermission?.let { onDeniedPermission() }
                }
            }
        }

        fun getPermissionLauncher(fragment: Fragment, onGrantedPermission: (()->Unit)?=null, onDeniedPermission: (()->Unit)?=null)
        : ActivityResultLauncher<Array<String>> {
            return fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                    permissions->
                val allGranted = permissions.all { it.value }
                if(allGranted){
                    onGrantedPermission?.let { onGrantedPermission() }
                } else {
                    onDeniedPermission?.let { onDeniedPermission() }
                }
            }
        }
    }
}