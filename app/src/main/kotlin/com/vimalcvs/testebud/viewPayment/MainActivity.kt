package com.vimalcvs.testebud.viewPayment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.theme.TesteBudTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteBudTheme {
                ScreenHomenew(navController = rememberNavController())
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavigationGraph(navController, Modifier.padding(paddingValues))
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "fragmentHome", modifier = modifier) {
        composable("fragmentHome") {
            ScreenHomenew(navController = navController)
        }
        composable("fragmentCategory") {
            ScreenHistory(navController = navController)
        }
        composable("fragmentFavorite") {
            ScreenTransaction(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination?.route == "fragmentHome",
            onClick = {
                navController.navigate("fragmentHome") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Category, contentDescription = "Category") },
            label = { Text("Category") },
            selected = currentDestination?.route == "fragmentCategory",
            onClick = {
                navController.navigate("fragmentCategory") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = currentDestination?.route == "fragmentFavorite",
            onClick = {
                navController.navigate("fragmentFavorite") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    TesteBudTheme {
        val navController = rememberNavController()
        MainScreen(navController)
    }
}
