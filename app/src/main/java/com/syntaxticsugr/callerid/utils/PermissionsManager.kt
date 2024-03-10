package com.syntaxticsugr.callerid.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.syntaxticsugr.callerid.enums.PermissionsResult

object PermissionsManager {

    val requiredPermissions = arrayOf(
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.CALL_PHONE
    )

    fun arePermissionsGranted(context: Context): PermissionsResult {
        val allGranted = requiredPermissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        return if (allGranted) {
            PermissionsResult.ALL_GRANTED
        } else {
            val canRequest = requiredPermissions.any {
                !ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, it)
            }

            if (canRequest) {
                PermissionsResult.CAN_REQUEST
            } else {
                PermissionsResult.PERMANENTLY_DENIED
            }
        }
    }

}
