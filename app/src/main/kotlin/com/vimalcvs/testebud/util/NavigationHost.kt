package com.vimalcvs.testebud.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.viewPayment.ScreenHistory
import com.vimalcvs.testebud.viewPayment.ScreenHomenew
import com.vimalcvs.testebud.viewPayment.ScreenTransaction

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable("home") { ScreenHomenew(navController) }
        composable("history") { ScreenHistory(navController) }
        composable("confirm") { ScreenTransaction(navController) }
    })
}