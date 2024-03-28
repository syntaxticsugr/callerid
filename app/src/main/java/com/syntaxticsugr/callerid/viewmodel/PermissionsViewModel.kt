package com.syntaxticsugr.callerid.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.syntaxticsugr.callerid.enums.PermissionsResult
import com.syntaxticsugr.callerid.navigation.Screens
import com.syntaxticsugr.callerid.utils.PermissionsManager
import com.syntaxticsugr.tcaller.utils.AuthKeyManager

class PermissionsViewModel(
    application: Application
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    fun openAppSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )

        activity.startActivity(intent)
    }

    fun nextScreen(navController: NavController) {
        val authKey = AuthKeyManager.getAuthKey(appContext)

        val destination = if (authKey == null) {
            Screens.LogIn.route
        } else {
            Screens.Home.route
        }

        navController.navigate(destination) {
            popUpTo(Screens.Permissions.route) {
                inclusive = true
            }
        }
    }

    fun getButtonText(context: Context): String {
        return when (PermissionsManager.arePermissionsGranted(context)) {
            PermissionsResult.CAN_REQUEST -> "Grant"
            PermissionsResult.ALL_GRANTED -> "Next"
            PermissionsResult.PERMANENTLY_DENIED -> "Settings"
        }
    }

}
