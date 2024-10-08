package com.example.tripdrop.ui.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tripdrop.R
import com.example.tripdrop.data.model.Product
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.common.standardPadding
import com.example.tripdrop.ui.theme.h2TextStyle
import com.example.tripdrop.ui.theme.h3TextStyle
import com.example.tripdrop.ui.theme.h4TextStyle
import com.example.tripdrop.viewModel.DropViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, vm: DropViewModel) {
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

    // Handle back press to exit the app
    BackHandler(true) {
        (navController.context as ComponentActivity).finish()
    }

    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 30.dp),
        topBar = { CustomTopAppBar(navController) }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(standardPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            var searchText by remember { mutableStateOf("") }
            val keyboardController = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                value = searchText,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search, contentDescription = "icon",
                        tint = Color(0xFFA7A7A7)
                    )
                },
                onValueChange = { searchText = it },
                shape = RoundedCornerShape(15.dp),
                placeholder = {
                    Text(
                        text = "Search Products...", color = Color(0xFFA7A7A7), fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.DarkGray,
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 14.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            // Filter products by search text and exclude the ones uploaded by the current user
            val filteredItems = productList.filter {
                it.title!!.contains(searchText, ignoreCase = true) &&
                        it.userId != currentUserId // Exclude products uploaded by the logged-in user
            }

            if (filteredItems.isEmpty()) {
                // Show Lottie animation and "No product uploaded" message if no products are available
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieAnimationEmpty()
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "No product uploaded",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(Color.White)
                    )
                }

            } else {
                // Display product cards dynamically
                filteredItems.forEach { product ->
                    ProductCard(product, onDetailsClick = {
                        navController.navigate(Route.ProductDetailScreen.name + "/${product.productId}")
                    })
                }
            }
        }
    }
}

@Composable
fun LottieAnimationEmpty() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
    val progress by animateLottieCompositionAsState(
        composition = composition, restartOnPlay = true, iterations = LottieConstants.IterateForever
    )

    LottieAnimation(modifier = Modifier.size(300.dp),
        composition = composition,
        progress = { progress })

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController: NavController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
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
                },
                style = h2TextStyle,
                fontSize = 30.sp
            )
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Route.FavouriteScreen.name)
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        })
}


@Composable
fun ProductCard(
    product: Product,
    onDetailsClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f),
                ) {
                    product.title?.let {
                        val truncatedTitle = if (it.length > 12) {
                            "${it.take(12)}..."
                        } else {
                            it
                        }
                        Text(
                            text =
                            truncatedTitle,
                            color = colorResource(id = R.color.black),
                            style = h3TextStyle,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))

//                     Truncate the description if it exceeds 44 characters
                    product.description?.let {
                        val truncatedDescription = if (it.length > 44) {
                            "${it.take(44)}..."
                        } else {
                            it
                        }
                        Text(
                            text = truncatedDescription,
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            style = h4TextStyle
                        )
                    }
                }


                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    painter = rememberImagePainter(data = product.imageUrl),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier
                    .padding(end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text =
                    "₹${product.rewards}",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Black)
                        .padding(4.dp)
                        .wrapContentSize()
                        .clickable {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT, """
                                    Check out this product on Dropit. :
                                    Title: ${product.title}
                                    Description: ${product.description}
                                    Price: ₹${product.rewards}
                                """.trimIndent()
                                )
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Share Product"
                                )
                            )
                        }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Icon",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Button(
                        onClick = {
                            onDetailsClick()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Details",
                            color = Color.White,
                            style = h3TextStyle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
