package com.syntaxticsugr.callerid

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.slaviboy.composeunits.initSize
import com.syntaxticsugr.callerid.navigation.SetupNavGraph
import com.syntaxticsugr.callerid.ui.theme.CallerIDTheme
import com.syntaxticsugr.callerid.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashViewModel = getViewModel<SplashViewModel>()

        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isLoading.value
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )

        initSize()

        setContent {
            CallerIDTheme {
                val startDestination by splashViewModel.startDestination
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, startDestination = startDestination)
            }
        }

    }

}
