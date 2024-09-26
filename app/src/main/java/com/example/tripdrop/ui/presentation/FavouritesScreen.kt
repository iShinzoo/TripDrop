package com.example.tripdrop.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tripdrop.viewModel.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.presentation.home.ProductCard

@Composable
fun FavoritesScreen(navController: NavController, vm: DropViewModel) {
    val favoriteProducts by vm.favoriteProducts.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Notifications Header
            Text(
                text = "Favorites",
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
                .padding(top = 88.dp), // Adjust this to fit the space occupied by the header
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (favoriteProducts.isEmpty()) {
                LottieAnimationFavourites()
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "No favorites added yet!",
                    modifier = Modifier,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favoriteProducts) { product ->
                        ProductCard(product = product, onDetailsClick = {
                            navController.navigate("productDetails/${product.productId}")
                        })
                    }
                }
            }
        }
    }
}
@Composable
fun LottieAnimationFavourites() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.favourites))
    val progress by animateLottieCompositionAsState(
        composition = composition, restartOnPlay = true, iterations = LottieConstants.IterateForever
    )

    LottieAnimation(modifier = Modifier.size(300.dp),
        composition = composition,
        progress = { progress })

}
