package com.vimalcvs.testebud.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.util.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteBudTheme {
                NavigationHost()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val navControllerBottom = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        containerColor = Color(0xFF1E1671),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFF1E1671)
                ),
                title = {
                    Text(
                        text = "Hi, Push Pushchair",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold

                    )
                },

                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_man),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Notification", Toast.LENGTH_SHORT).show()
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "Profile"
                        )
                    }
                },
            )
        }, bottomBar = {
            BottomNavigationBar(navControllerBottom)
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationGraph(navController, navControllerBottom)
        }
    }
}


@Composable
fun NavigationGraph(navController: NavHostController, navControllerBottom: NavHostController) {
    NavHost(
        navControllerBottom,
        startDestination = "fragmentHome",
    ) {
        composable(
            "fragmentHome",
        ) {
            ScreenHome(navController = navController)
        }

        composable(
            "fragmentCategory",

            ) {
            ScreenHome(navController = navController)
        }

        composable(
            "fragmentFavorite",

            ) {
            ScreenHome(navController = navController)
        }


        composable(
            "fragmentSettings",
        ) {
            ScreenHome(navController = navController)
        }

    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    NavigationBar(
        containerColor = Color(0xFFFFFFFF),
        contentColor = Color(0xFF757575),
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                    modifier = Modifier.size(22.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF3629B7).copy(0.1f)
            ),
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
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Category",
                    modifier = Modifier.size(22.dp)
                )

            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF3629B7).copy(0.1f)
            ),
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
            icon = {

                Icon(
                    painter = painterResource(id = R.drawable.ic_massage),
                    contentDescription = "Favorites",
                    modifier = Modifier.size(22.dp)
                )

            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF3629B7).copy(0.1f)
            ),
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

        NavigationBarItem(
            icon = {

                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    modifier = Modifier.size(22.dp)
                )

            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFF3629B7).copy(0.1f)
            ),
            selected = currentDestination?.route == "fragmentSettings",
            onClick = {
                navController.navigate("fragmentSettings") {
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
