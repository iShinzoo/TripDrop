package com.example.tripdrop.ui.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.tripdrop.ui.presentation.common.CommonImage
import com.example.tripdrop.ui.presentation.common.DefaultPadding
import com.example.tripdrop.ui.presentation.common.IconSize
import com.example.tripdrop.ui.presentation.common.LargePadding
import com.example.tripdrop.ui.presentation.common.LoaderScreen
import com.example.tripdrop.ui.presentation.common.SmallSpacing

@Composable
fun ProfileScreen(navController: NavController, vm: DropViewModel) {
    val userData by vm.userDetails.collectAsState()

    if (userData == null) {
        // Show loading indicator while waiting for user data
        LoaderScreen()
    } else {
        val name = userData?.name ?: ""
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
                    name = name,
                    onMove = { navController.navigate(route = Route.ProfileDetailScreen.name) },
                    imageUrl = imageUrl
                )
                Spacer(modifier = Modifier.height(SmallSpacing))
                ProfileOptionButtons(navController)
            }
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
fun UserDetailsCard(name: String,onMove: () -> Unit, imageUrl: String?) {
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
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
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
        CustomButton(text = text, leadingIcon = icon) {
            when (text) {
                "Your Orders" -> navController.navigate(Route.YoursOrderScreen.name)
                "Payments" -> navController.navigate(Route.PaymentScreen.name)
                "Help" -> navController.navigate(Route.HelpScreen.name)
                "Policies" -> navController.navigate(Route.PolicyScreen.name)
                "Feedback Form" -> navController.navigate(Route.FeedbackScreen.name)
            }
        }
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
