package com.example.tripdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.tripdrop.ui.navigation.NavGraph
import com.example.tripdrop.ui.presentation.NoNetworkScreen
import com.example.tripdrop.ui.presentation.common.LoaderScreen
import com.example.tripdrop.ui.presentation.common.RequestAppPermissionsScreen
import com.example.tripdrop.ui.theme.TripDropTheme
import com.example.tripdrop.viewModel.ChatViewModel
import com.example.tripdrop.viewModel.DropViewModel
import com.example.tripdrop.viewModel.NetworkViewModel
import com.example.tripdrop.viewModel.NotificationViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: DropViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()
    private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        actionBar?.hide()
        installSplashScreen()
        setContent {
            TripDropTheme {
                val systemController = rememberSystemUiController()
                val isConnected by networkViewModel.isConnected.collectAsState()

                SideEffect {
                    systemController.setStatusBarColor(
                        color = Color.White,
                        darkIcons = true
                    )
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    when {
                        isConnected -> {
                            // Show the main app content when connected
                            AnimatedVisibility(
                                visible = isConnected,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                NavGraph(vm = viewModel, chatViewModel, notificationViewModel,this@MainActivity)
                            }
                        }
                        else -> {
                            // Show the No Network screen when disconnected
                            AnimatedVisibility(
                                visible = !isConnected,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                NoNetworkScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
