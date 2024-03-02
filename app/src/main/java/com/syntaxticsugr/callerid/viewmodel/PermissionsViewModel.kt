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
import com.syntaxticsugr.callerid.utils.AuthKeyManager
import com.syntaxticsugr.callerid.utils.PermissionsManager

class PermissionsViewModel(
    application: Application
) : ViewModel() {

    private val appContext: Context = application.applicationContext

    fun getButtonText(context: Context): String {
        return when (PermissionsManager.arePermissionsGranted(context)) {
            PermissionsResult.CAN_REQUEST -> "Grant"
            PermissionsResult.ALL_GRANTED -> "Next"
            PermissionsResult.PERMANENTLY_DENIED -> "Settings"
        }
    }

    fun openAppSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        activity.startActivity(intent)
    }

    fun nextScreen(navController: NavController) {
        if (AuthKeyManager.getAuthKey(appContext) != null) {
            navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Permissions.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screens.LogIn.route) {
                popUpTo(Screens.Permissions.route) {
                    inclusive = true
                }
            }
        }
    }

}
