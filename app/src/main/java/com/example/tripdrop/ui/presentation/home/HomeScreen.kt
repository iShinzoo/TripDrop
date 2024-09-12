package com.example.tripdrop.ui.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize() // Fill the screen
            .padding(12.dp) // Add padding for better spacing on different screens
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(12.dp))
            DetailsCard {
                navController.navigate(route = Route.ProductDetailsScreen.route)
            }
            DetailsCard {
                navController.navigate(route = Route.ProductDetailsScreen.route)
            }
            DetailsCard {
                navController.navigate(route = Route.ProductDetailsScreen.route)
            }
            DetailsCard {
                navController.navigate(route = Route.ProductDetailsScreen.route)
            }
            DetailsCard {
                navController.navigate(route = Route.ProductDetailsScreen.route)
            }
        }
        // TopAppBar fixed at the top
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.TopStart)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("Drop")
                    }
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("it")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(".")
                    }
                }, fontSize = 36.sp, fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.white)),
        modifier = modifier
    )
}

@Composable
fun SearchBar() {

    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(32.dp)),
        placeholder = {
            Text(
                text = "Search", color = Color.Black
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Black
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,  // Removes the black border when focused
            unfocusedBorderColor = Color.Transparent, // Removes the black border when unfocused
            disabledBorderColor = Color.Transparent,  // Ensures the border is transparent when disabled
            errorBorderColor = Color.Transparent,     // Ensures the border is transparent when there's an error
            focusedTextColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}

@Composable
fun DetailsCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(235.dp) // Adjusted to your requirement of 400dp height
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Product Info (Product Name, Description, and Image)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f), // Adjusted to use available space
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Product Name",
                        color = colorResource(id = R.color.black),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Product Description",
                        color = colorResource(id = R.color.black),
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp)) // Space between text and image

                Image(
                    painter = rememberImagePainter(data = "https://via.placeholder.com/100"),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .align(Alignment.Bottom)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Product Price, Share Icon, and Details Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "₹999",
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(16.dp)) // Ensure consistent spacing

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp)) // Space between icon and button

                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Details")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}