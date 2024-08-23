package com.example.tripdrop.presentation

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.R
import com.example.tripdrop.navigation.Route
import java.util.Calendar

@Composable
fun PostScreen(navController: NavController) {

    val context = LocalContext.current

    var productName by remember { mutableStateOf("") }
    var productDesc by remember { mutableStateOf("") }
    var pickupPoint by remember { mutableStateOf("") }
    var destinationPoint by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    // Date Picker
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = android.app.DatePickerDialog(
        context, { _, selectedYear, selectedMonth, selectedDay ->
            date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    // Time Picker
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context, { _, selectedHour, selectedMinute ->
            time = String.format("%02d:%02d", selectedHour, selectedMinute)
        }, hour, minute, false
    )

    Box(
        modifier = Modifier.background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column {
                Text(
                    text = "Product Details",
                    modifier = Modifier
                        .width(320.dp)
                        .align(Alignment.Start)
                        .padding(top = 60.dp),
                    color = colorResource(id = R.color.HighlightText),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.height(50.dp))

            // Product Name Field

            OutlinedTextField(
                modifier = Modifier
                    .width(320.dp)
                    .background(colorResource(id = R.color.white)),

                value = productName,

                onValueChange = { productName = it },

                leadingIcon = {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText)
                    )
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),

                placeholder = {
                    Text(
                        text = "Enter Your Product Name",
                        color = colorResource(id = R.color.HighlightText)
                    )
                },

                singleLine = true
            )
            Spacer(modifier = Modifier.height(18.dp))


            // Product description Field


            OutlinedTextField(
                modifier = Modifier
                    .width(320.dp)
                    .background(colorResource(id = R.color.white)),

                value = productDesc,

                onValueChange = { productDesc = it },

                leadingIcon = {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText)
                    )
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),

                placeholder = {
                    Text(
                        text = "Enter Product Description",
                        color = colorResource(id = R.color.HighlightText)
                    )
                },

                singleLine = true
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Product pick up Field

            OutlinedTextField(
                modifier = Modifier
                    .width(320.dp)
                    .background(colorResource(id = R.color.white)),

                value = pickupPoint,

                onValueChange = { pickupPoint = it },

                placeholder = {
                    Text(
                        text = "Add Pick Up Location",
                        color = colorResource(id = R.color.HighlightText)
                    )
                }, singleLine = true,

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.AddLocation,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText)
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Product destination Field

            OutlinedTextField(
                modifier = Modifier
                    .width(320.dp)
                    .background(colorResource(id = R.color.white)),

                value = destinationPoint,

                onValueChange = { destinationPoint = it },

                placeholder = {
                    Text(
                        text = "Add Destination", color = colorResource(id = R.color.HighlightText)
                    )
                }, singleLine = true,

                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.AddLocation,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText)
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Date Picker TextField

            OutlinedTextField(
                value = date,

                onValueChange = {},

                modifier = Modifier
                    .width(320.dp)
                    .clickable { datePickerDialog.show() },

                placeholder = {
                    Text(
                        "Select Date",
                        color = colorResource(id = R.color.HighlightText)
                    )
                },

                readOnly = true,

                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),

                leadingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText),
                        modifier = Modifier.clickable {
                            datePickerDialog.show()
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Time Picker TextField

            OutlinedTextField(
                value = time,

                onValueChange = {},

                modifier = Modifier
                    .width(320.dp)
                    .clickable { timePickerDialog.show() },

                placeholder = {
                    Text(
                        "Select Time",
                        color = colorResource(id = R.color.HighlightText)
                    )
                },
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                leadingIcon = {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText),
                        modifier = Modifier.clickable {
                            timePickerDialog.show()
                        }
                    )
                },
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Product Price Field

            OutlinedTextField(
                modifier = Modifier
                    .width(320.dp)
                    .background(colorResource(id = R.color.white)),

                value = productPrice,

                onValueChange = { productPrice = it },

                leadingIcon = {
                    Icon(
                        Icons.Default.CurrencyRupee,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText)
                    )
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),

                placeholder = {
                    Text(
                        text = "Enter Charges For Delivery",
                        color = colorResource(id = R.color.HighlightText)
                    )
                },

                singleLine = true
            )
            Spacer(modifier = Modifier.height(18.dp))

            // Product Confirm Button

            Button(
                onClick = {
                    navController.navigate(route = Route.HomeScreen.route)
                },
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(colorResource(id = R.color.ButtonsBackground))
            ) {
                Text(
                    text = "Confirm",
                    modifier = Modifier,
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Preview
@Composable
fun PostScreenPreview() {
    PostScreen(navController = rememberNavController())
}