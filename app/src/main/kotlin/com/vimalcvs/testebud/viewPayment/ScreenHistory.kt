package com.vimalcvs.testebud.viewPayment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vimalcvs.testebud.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHistory(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Payment History", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F9))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            PaymentHistoryTabs()

            Spacer(modifier = Modifier.height(16.dp))
            PaymentHistoryList()
        }
    }
}

@Composable
fun PaymentHistoryTabs() {
    var selectedTab by remember { mutableStateOf("Internet") }
    val tabs = listOf("Water", "Mobile", "Internet", "Gas")
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Text(
                text = tab,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else Color.Black,
                modifier = Modifier
                    .background(
                        color = if (isSelected) Color(0xFF3629B7) else Color(0xFFF2F1F9),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { selectedTab = tab }
            )
        }
    }
}


@Composable
fun PaymentHistoryList() {
    val paymentHistory = listOf(
        PaymentHistoryItem(
            "October",
            "Unsuccessfully",
            Color(0xFFFF4267),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "September",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "August",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "July",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "June",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "October",
            "Unsuccessfully",
            Color(0xFFFF4267),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "September",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "August",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "July",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "June",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "October",
            "Unsuccessfully",
            Color(0xFFFF4267),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "September",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "August",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "July",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "June",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "October",
            "Unsuccessfully",
            Color(0xFFFF4267),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "September",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "August",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "July",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        ),
        PaymentHistoryItem(
            "June",
            "Successfully",
            Color(0xFF3629B7),
            "Capi Telecom",
            "$50",
            "30/10/2019"
        )

    )

    LazyColumn {
        items(paymentHistory.size) { index ->
            PaymentHistoryCard(paymentHistory[index])
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PaymentHistoryCard(item: PaymentHistoryItem) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.5.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)

    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.month,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF343434),
                    fontSize = 16.sp
                )
                Text(
                    text = item.date,
                    fontSize = 14.sp,
                    color = Color(0xFF979797),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(text = "Status", fontSize = 14.sp, color = Color(0xFF979797))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.status,
                        fontSize = 14.sp,
                        color = item.statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    Text(text = "Amount", fontSize = 14.sp, color = Color(0xFF979797))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.amount,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3629B7)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = "Company", fontSize = 14.sp, color = Color(0xFF979797))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.company,
                    fontSize = 14.sp,
                    color = Color(0xFF3629B7),
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

data class PaymentHistoryItem(
    val month: String,
    val status: String,
    val statusColor: Color,
    val company: String,
    val amount: String,
    val date: String
)

