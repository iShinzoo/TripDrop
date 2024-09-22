package com.example.tripdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tripdrop.ui.navigation.NavGraph
import com.example.tripdrop.ui.theme.TripDropTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: DropViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        Thread.sleep(3000)
//        installSplashScreen().apply {
//            setKeepOnScreenCondition {
//                viewModel.isLoading.value
//            }
//        }
        setContent {
            TripDropTheme {

                // sideEffect(setStatusBarColor) is used to customize the status bar of a Device
                // but before this you need to Do Dynamic Color value to false in Theme.kt

                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setStatusBarColor(
                        color = Color.Black,
                    )
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavGraph(vm = viewModel,chatViewModel,notificationViewModel)
                }
            }
        }
    }
}

