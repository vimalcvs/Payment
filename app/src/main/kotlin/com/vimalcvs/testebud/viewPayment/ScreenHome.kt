package com.vimalcvs.testebud.viewPayment


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHomenew(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = Color(0xFF0A3D91)
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
                    navController.navigate("home")
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_man),
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate("profile")
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Profile"
                    )
                }
            },

            )
    }, bottomBar = {
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF0A3D91))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                    .background(Color(0xFFF7F7F7))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CardSection()
                    ActionGrid(navController = navController)

                }


            }


        }

    }
}

@Composable
fun CardSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(230.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(216.dp)
                .padding(start = 22.dp, end = 22.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFFF4267))

        ) {

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(208.dp)
                .padding(start = 12.dp, end = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF5655B9))
        ) {

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1E1671))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_card),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "John Smith",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Amazon Platinum", fontSize = 18.sp, color = Color.White
                        )
                        Text(
                            text = "4756 **** **** 9018", fontSize = 18.sp, color = Color.White
                        )
                        Text(
                            text = "$3,469.52",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActionGrid(navController: NavController = rememberNavController()) {
    val actions = listOf(
        "Account and Card" to R.drawable.ic_account,
        "Transfer" to R.drawable.ic_transfer,
        "Withdraw" to R.drawable.ic_withdraw,
        "Mobile prepaid" to R.drawable.ic_mobile,
        "Pay the bill" to R.drawable.ic_receipt,
        "Save online" to R.drawable.ic_save_online,
        "Credit card" to R.drawable.ic_credit_card,
        "Transaction report" to R.drawable.ic_transaction,
        "Beneficiary" to R.drawable.ic_beneficiary
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(actions) { action ->
            ActionItem(title = action.first, iconRes = action.second, navController)
        }
    }
}

@Composable
fun ActionItem(
    title: String, iconRes: Int, navController: NavController = rememberNavController()
) {
    Card(
        onClick = {
            when (title) {
                "Transfer" -> navController.navigate("confirm")
                "Pay the bill" -> navController.navigate("history")
                else -> {
                    // Handle other actions as needed
                }
            }
        }, colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 0.1.dp
        ), modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0x45E8EAF6))
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFF757575),
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center

            )
        }
    }
}




