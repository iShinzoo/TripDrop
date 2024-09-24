package com.example.tripdrop.ui.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tripdrop.R // Ensure this import for default image resource

@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Fit
) {
    // Define the brush for the border
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }

    // Use a default image resource if `data` is null or empty
    val painter = rememberAsyncImagePainter(
        model = data,
        placeholder = painterResource(id = R.drawable.account), // Replace with your drawable resource
        error = painterResource(id = R.drawable.account) // Use default image on error
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(150.dp)
            .border(
                BorderStroke(4.dp, rainbowColorsBrush),
                CircleShape
            )
            .padding(4.dp),
        contentScale = contentScale
    )
}
