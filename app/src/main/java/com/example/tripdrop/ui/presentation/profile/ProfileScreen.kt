package com.example.tripdrop.ui.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.CommonImage
import com.example.tripdrop.ui.presentation.DefaultPadding
import com.example.tripdrop.ui.presentation.IconSize
import com.example.tripdrop.ui.presentation.LargePadding
import com.example.tripdrop.ui.presentation.SmallSpacing

@Composable
fun ProfileScreen(navController: NavController, vm: DropViewModel) {
    val userData = vm.userData.value
    val imageUrl = userData?.imageUrl

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
            ProfileHeader()
            Spacer(modifier = Modifier.height(LargePadding))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 88.dp, // Adjust based on header height
                    start = DefaultPadding,
                    end = DefaultPadding
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            UserDetailsCard(
                onMove = { navController.navigate(route = Route.ProfileDetailScreen.name) },
                imageUrl = imageUrl
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            ProfileOptionButtons(navController)
        }
    }
}

@Composable
fun ProfileHeader() {
    Text(
        text = "Profile",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = LargePadding, start = SmallSpacing),
        color = colorResource(id = R.color.black),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun UserDetailsCard(onMove: () -> Unit, imageUrl: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SmallSpacing),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(DefaultPadding)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CommonImage(
                    data = imageUrl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(DefaultPadding))
                Column {
                    Text(
                        text = "Aman Nishant",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "9893467890",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(DefaultPadding))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onMove,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Edit Details")
            }
        }
    }
}

@Composable
fun ProfileOptionButtons(navController: NavController) {
    val buttons = listOf(
        "Your Orders" to Icons.Default.DeliveryDining,
        "Payments" to Icons.Default.Payment,
        "Help" to Icons.Default.QuestionMark,
        "Policies" to Icons.Default.Policy,
        "Feedback Form" to Icons.Default.Forum
    )
    buttons.forEach { (text, icon) ->
        Spacer(modifier = Modifier.height(SmallSpacing))
        CustomButton(text = text, leadingIcon = icon) { /* Navigate as required */ }
    }
}

@Composable
fun CustomButton(
    text: String,
    leadingIcon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(SmallSpacing)
            .height(48.dp),
        contentPadding = PaddingValues(horizontal = DefaultPadding),
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
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(IconSize)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(IconSize)
            )
        }
    }
}
