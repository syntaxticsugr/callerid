package com.syntaxticsugr.callerid.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.enums.PermissionsResult
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.permissions.requiredPermissions
import java.io.File

class PermissionsViewModel(
    application: Application
) : ViewModel() {

    private val appContext: Context = application.applicationContext
    private val asd = appContext.filesDir
    private val authfile = "authkey.json"

    fun nextScreen(navController: NavController) {
        val authKey = File(asd, authfile)

        if (authKey.exists()) {
            navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Home.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screens.LogIn.route) {
                popUpTo(Screens.LogIn.route) {
                    inclusive = true
                }
            }
        }
    }

    fun openAppSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        activity.startActivity(intent)
    }

    private fun arePermissionsGranted(context: Context): PermissionsResult {
        val allPermissionsGranted = requiredPermissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        return if (allPermissionsGranted) {
            PermissionsResult.ALL_GRANTED
        } else {
            val canRequestSettings = requiredPermissions.any {
                !ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, it)
            }

            if (canRequestSettings) {
                PermissionsResult.CAN_REQUEST
            } else {
                PermissionsResult.PERMANENTLY_DENIED
            }
        }
    }

    fun getButtonText(context: Context): String {
        return when (arePermissionsGranted(context)) {
            PermissionsResult.CAN_REQUEST -> "Grant"
            PermissionsResult.ALL_GRANTED -> "Next"
            PermissionsResult.PERMANENTLY_DENIED -> "Settings"
        }
    }

}
