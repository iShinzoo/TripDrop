package com.example.tripdrop.ui.presentation.post

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import java.util.Calendar

/**
 * Composable function that represents the Post screen in the TripDrop app.
 * This screen allows users to input details about a product they want to post, including the product name, description, image, pickup and destination locations, date, time, and price.
 * The function also handles the logic for uploading the product details to the app's backend and navigating to the home screen after a successful post.
 *
 * @param vm The DropViewModel instance, which provides the necessary functionality for uploading the product details.
 * @param navController The NavController instance, which is used for navigating to the home screen after a successful post.
 */

@Composable
fun PostScreen(vm: DropViewModel, navController: NavController) {

    val context = LocalContext.current

    var productName by remember { mutableStateOf("") }
    var productDesc by remember { mutableStateOf("") }
    var pickupPoint by remember { mutableStateOf("") }
    var destinationPoint by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var productImageUri by remember { mutableStateOf<Uri?>(null) }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            date = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context, { _, hour, minute ->
            time = String.format("%02d:%02d", hour, minute)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> productImageUri = uri })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {

            Text(
                text = "Product Details",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                color = colorResource(id = R.color.black),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 88.dp) // Padding to fit header space
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Product Name Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = productName,
                onValueChange = { productName = it },
                leadingIcon = {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                placeholder = {
                    Text(
                        text = "Enter Your Product Name", color = colorResource(id = R.color.Gray)
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = productDesc,
                onValueChange = { productDesc = it },
                leadingIcon = {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                placeholder = {
                    Text(
                        text = "Enter Product Description", color = colorResource(id = R.color.Gray)
                    )
                },
                singleLine = false,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .clickable { imagePickerLauncher.launch("image/*") },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    productImageUri?.let { uri ->
                        // Display the selected image
                        Image(
                            painter = rememberImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run {
                        // Display Lottie animation if no image is selected
                        LottieAnimationUploadImage()
                    }
                    Text(
                        text = "Add Image",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = pickupPoint,
                onValueChange = { pickupPoint = it },
                placeholder = {
                    Text(
                        text = "Add Pick Up Location", color = colorResource(id = R.color.Gray)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.AddLocation,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = destinationPoint,
                onValueChange = { destinationPoint = it },
                placeholder = {
                    Text(
                        text = "Add Destination", color = colorResource(id = R.color.Gray)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.AddLocation,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { datePickerDialog.show() },
                placeholder = {
                    Text(text = "Select Date",
                        color = colorResource(id = R.color.Gray),
                        modifier = Modifier.clickable {
                            datePickerDialog.show()
                        })
                },
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                leadingIcon = {
                    Icon(Icons.Default.DateRange,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray),
                        modifier = Modifier.clickable {
                            datePickerDialog.show()
                        })
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = time,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { timePickerDialog.show() },
                placeholder = {
                    Text(text = "Select Time",
                        color = colorResource(id = R.color.Gray),
                        modifier = Modifier.clickable {
                            timePickerDialog.show()
                        })
                },
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                leadingIcon = {
                    Icon(Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray),
                        modifier = Modifier.clickable {
                            timePickerDialog.show()
                        })
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = productPrice,
                onValueChange = { productPrice = it },
                leadingIcon = {
                    Icon(
                        Icons.Default.CurrencyRupee,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = {
                    Text(
                        text = "Enter Product Price", color = colorResource(id = R.color.Gray)
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (productName.isNotEmpty() && productDesc.isNotEmpty() && productImageUri != null && pickupPoint.isNotEmpty() && destinationPoint.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty() && productPrice.isNotEmpty()) {

                        vm.uploadProductDetails(
                            title = productName,
                            description = productDesc,
                            imageUri = productImageUri.toString(),
                            pickupPoint = pickupPoint,
                            deliveryPoint = destinationPoint,
                            time = time,
                            date = date,
                            rewards = productPrice,
                            context = context
                        )

                        Toast.makeText(context, "Product Posted Successfully", Toast.LENGTH_SHORT)
                            .show()

                        navController.navigate(Route.HomeScreen.name) {
                            popUpTo("post") { inclusive = true }
                        }

                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Post",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun LottieAnimationUploadImage() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.upload))
    val progress by animateLottieCompositionAsState(
        composition = composition, restartOnPlay = true, iterations = LottieConstants.IterateForever
    )

    LottieAnimation(modifier = Modifier.size(300.dp),
        composition = composition,
        progress = { progress })

}