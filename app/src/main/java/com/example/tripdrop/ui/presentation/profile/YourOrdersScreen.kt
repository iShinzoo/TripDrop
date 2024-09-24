package com.example.tripdrop.ui.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.data.model.Product
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.common.DefaultPadding
import com.example.tripdrop.ui.presentation.home.ProductCard

@Composable
fun YourOrdersScreen(navController: NavController, vm: DropViewModel) {

    var productList by remember { mutableStateOf(listOf<Product>()) }
    var currentUserId by remember { mutableStateOf("") }

    // Fetch current logged-in user's ID
    LaunchedEffect(Unit) {
        vm.getCurrentUserId { userId ->
            currentUserId = userId
        }
    }

    // Fetch product data from Firestore
    LaunchedEffect(Unit) {
        vm.fetchProductsFromFirestore { products ->
            productList = products
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DefaultPadding)
                .align(Alignment.TopStart)
        ) {
            // Notifications Header
            Text(
                text = "Your Orders",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 8.dp),
                color = colorResource(id = R.color.black),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 88.dp, // Adjust based on header height
                    start = DefaultPadding,
                    end = DefaultPadding
                ) // Adjust this to fit the space occupied by the header
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Filter products by search text and exclude the ones uploaded by the current user
            val filteredItems = productList.filter {
                        it.userId == currentUserId // Exclude products uploaded by the logged-in user
            }

            // Display product cards dynamically
            filteredItems.forEach { product ->
                ProductCard(product, onDetailsClick = {
                    navController.navigate(Route.ProductDetailScreen.name + "/${product.productId}")
                })
            }

        }
    }
}