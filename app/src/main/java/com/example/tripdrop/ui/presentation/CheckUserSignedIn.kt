package com.example.tripdrop.ui.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.ui.navigation.Route

@Composable
fun CheckUserSignedIn(vm: DropViewModel, navController: NavController) {
    val alreadySignedIn = remember {
        mutableStateOf(false)
    }

    val signIn = vm.signIn.value

    if (signIn && !alreadySignedIn.value) {
        alreadySignedIn.value = true
        navController.navigate(Route.BottomNav.route) {
            popUpTo(0)
        }
    }

}