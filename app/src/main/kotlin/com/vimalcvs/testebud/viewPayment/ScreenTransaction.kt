package com.vimalcvs.testebud.viewPayment

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vimalcvs.testebud.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTransaction(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirm", fontSize = 20.sp) },
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
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    ConfirmTransactionForm()
                }
            }
            ConfirmButton()
        }
    }
}

@Composable
fun ConfirmTransactionForm() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Confirm transaction information",
            fontSize = 14.sp,
            color = Color(0xFF989898),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        ConfirmTextField(label = "From", value = "**** **** 6789")
        ConfirmTextField(label = "To", value = "Amanda")
        ConfirmTextField(label = "Card number", value = "0123456789")
        ConfirmTextField(label = "Transaction fee", value = "10$")
        ConfirmTextField(label = "Content", value = "From Jimy")
        ConfirmTextField(label = "Amount", value = "$1000")

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            ConfirmTextField(
                label = "Get OTP to verify transaction",
                value = "6789",
                modifier = Modifier.weight(1f),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {

                },
                modifier = Modifier
                    .padding(top = 28.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3629B7),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Get OTP", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ConfirmTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF979797),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {
                // Handle text change
            },
            enabled = enabled,
            shape = RoundedCornerShape(20.dp),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF5C6BC0),
                unfocusedBorderColor = Color(0xFF989898),
            )
        )
    }
}

@Composable
fun ConfirmButton() {
    Button(
        onClick = {
            // Handle button click
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3629B7),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Confirm", color = Color.White, fontSize = 18.sp)
    }
}


@Preview
@Composable
fun PreviewScreenTransaction() {
    ScreenTransaction(navController = NavController(LocalContext.current))
}