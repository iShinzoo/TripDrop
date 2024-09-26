package com.example.tripdrop.ui.presentation.home.details

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.tripdrop.viewModel.ChatViewModel
import com.example.tripdrop.viewModel.DropViewModel
import com.example.tripdrop.viewModel.NotificationViewModel
import com.example.tripdrop.R
import com.example.tripdrop.data.model.Product
import com.example.tripdrop.data.model.UserData
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.common.LoaderScreen
import com.example.tripdrop.ui.presentation.common.standardPadding
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    vm: DropViewModel,
    productId: String,
    nm: NotificationViewModel,
    chatModel: ChatViewModel
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
        Column {
            productDetails?.let { product ->
                isLoading = false
                ProductContent(navController = navController, vm, product = product)
            } ?: run {
                if (isLoading) {
                    LoaderScreen()
                } else {
                    ErrorView { vm.fetchProductDetails(productId) }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Handle loading and error states
                productDetails?.let { product ->
                    isLoading = false
                    ProductDetailsContent(product, navController, nm, chatModel, vm)
                } ?: run {
                    if (isLoading) {
                        LoaderScreen()
                    } else {
                        ErrorView { vm.fetchProductDetails(productId) }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailsContent(
    product: Product,
    navController: NavController,
    nm: NotificationViewModel,
    chatModel: ChatViewModel,
    vm: DropViewModel
) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val productOwner = remember { mutableStateOf<UserData?>(null) } // Product owner info

    // Fetch product details and owner data here (assuming a function exists to fetch it)
    LaunchedEffect(product.productId) {
        vm.getProductOwner(product.productId) { owner ->
            productOwner.value = owner
        }
    }
    val fcmToken by nm.fcmToken.observeAsState()
    val productName = product.title

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
            onClick = {

                if (currentUser == null) {
                    Log.e("AuthError", "User is not authenticated!")
                } else {
                    val user1 = currentUser.uid // Current user
                    val user2 = productOwner.value?.userId // Product owner

                    if (user2 != null) {
                        chatModel.getOrCreateChat(user1, user2) { chatId ->
                            if (chatId != null) {
                                Log.d(
                                    "ProductDetailsScreen",
                                    "Navigating to chat with chatId: $chatId"
                                )
                                navController.navigate(route = "${Route.SingleChatScreen.name}/{chatId}")
                            } else {
                                Log.e("ProductDetailsScreen", "Failed to create or retrieve chat!")
                            }
                        }
                    } else {
                        Log.e("ProductDetailsScreen", "User1 or User2 is null!")
                    }
                }
            }
        )

        ProductActionButton(
            icon = Icons.Default.DeliveryDining,
            buttonText = "Ready to Drop it?",
            onClick = {
                fcmToken?.let { token ->
                    productName?.let { name ->
                        nm.sendNotification(token, name)
                    }
                }
            }
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
fun ProductDetailText(
    label: String,
    value: String,
    isTitle: Boolean = false,
    isReward: Boolean = false
) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductContent(
    navController: NavController,
    vm: DropViewModel,
    product: Product

) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .padding(start = standardPadding, end = standardPadding)
    ) {
        TopAppBar(
            title = { AppBarTitleProfile() },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Route.HomeScreen.name) }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    vm.addProductToFavorites(product) // Add product to favorites
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.Favorite, contentDescription = "Save", tint = Color.Black)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.white))
        )

        Spacer(modifier = Modifier.height(18.dp))

    }
}

@Composable
fun AppBarTitleProfile() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Product Details",
            color = Color.Black,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
