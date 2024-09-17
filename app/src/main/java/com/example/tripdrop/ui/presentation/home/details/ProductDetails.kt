package com.example.tripdrop.ui.presentation.home.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.data.Product
import com.example.tripdrop.ui.navigation.Route

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    vm: DropViewModel,
    productId: String
) {
    val productDetails by vm.productDetails.observeAsState()
    var isLoading by remember { mutableStateOf(true) }

    // Fetch product details when the screen is displayed
    LaunchedEffect(productId) {
        vm.fetchProductDetails(productId)
    }

    // Main layout container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ProductHeader(navController)

            // Handle loading and error states
            productDetails?.let { product ->
                isLoading = false
                ProductDetailsContent(product, navController)
            } ?: run {
                if (isLoading) {
                    LoadingView()
                } else {
                    ErrorView { vm.fetchProductDetails(productId) }
                }
            }
        }
    }
}

@Composable
fun ProductHeader(navController: NavController) {
    Row(
        modifier = Modifier.padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .size(32.dp)
                .clickable { navController.navigate(Route.HomeScreen.name) }
        )
        Text(
            text = "Product Details",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            color = colorResource(id = R.color.black),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductDetailsContent(product: Product, navController: NavController) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        product.imageUrl?.let { ProductImageCard(it) }

        Spacer(modifier = Modifier.height(16.dp))

        product.title?.let { ProductDetailText("Title", it, isTitle = true) }
        product.description?.let { ProductDetailText("Description", it) }
        product.rewards?.let { ProductDetailText("Rewards", it, isReward = true) }
        product.pickupPoint?.let { ProductDetailText("Pickup Point", it) }
        product.deliveryPoint?.let { ProductDetailText("Delivery Point", it) }
        product.time?.let { ProductDetailText("Time", it) }
        product.date?.let { ProductDetailText("Date", it) }

        Spacer(modifier = Modifier.height(16.dp))

        ProductActionButton(
            icon = Icons.Default.Chat,
            buttonText = "Chat with User",
            onClick = { navController.navigate(Route.SingleChatScreen.name) }
        )

        ProductActionButton(
            icon = Icons.Default.DeliveryDining,
            buttonText = "Ready to Drop it?",
            onClick = { /* Handle Drop Action */ }
        )
    }
}

@Composable
fun ProductImageCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun ProductDetailText(label: String, value: String, isTitle: Boolean = false, isReward: Boolean = false) {
    if (isTitle || isReward) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("$label: ")
                }
                withStyle(style = SpanStyle(color = if (isReward) Color.Green else Color.Black)) {
                    append(value)
                }
            },
            fontSize = if (isTitle) 24.sp else 16.sp,
            fontWeight = if (isTitle) FontWeight.Bold else FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = if (isTitle) 8.dp else 4.dp)
        )
    } else {
        Text(
            text = "$label: $value",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}


@Composable
fun ProductActionButton(icon: ImageVector, buttonText: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
            Text(
                text = buttonText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun ErrorView(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Failed to load details",
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}
