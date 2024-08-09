package com.vimalcvs.testebud.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.view.MainScreen
import com.vimalcvs.testebud.view.ScreenConfirm
import com.vimalcvs.testebud.view.ScreenPayment

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home", builder = {
        composable("home") { MainScreen(navController) }
        composable("payment") { ScreenPayment(navController) }
        composable("confirm") { ScreenConfirm(navController) }
    })
}