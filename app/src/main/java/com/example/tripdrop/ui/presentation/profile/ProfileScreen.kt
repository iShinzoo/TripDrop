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

@Composable
fun ProfileScreen(navController: NavController, vm: DropViewModel) {

    val userData = vm.userData.value
    val imageUrl = userData?.imageUrl

    // Main container for the Profile Screen
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
            // Header with profile title
            Text(
                text = "Profile",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 8.dp),
                color = colorResource(id = R.color.black),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp)) // Space between header and content
        }

        // Scrollable content below the header
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 88.dp, // Adjust based on the height of the header
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // User details card
            UserDetailsCard(
                onMove = { navController.navigate(route = Route.ProfileDetailScreen.name) },
                imageUrl = imageUrl
            )
            Spacer(modifier = Modifier.height(16.dp)) // Space between items
            // Buttons for additional profile options
            CustomButton(text = "Your Orders", leadingIcon = Icons.Default.DeliveryDining) { }
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(text = "Payments", leadingIcon = Icons.Default.Payment) { }
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(text = "Help", leadingIcon = Icons.Default.QuestionMark) { }
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(text = "Policies", leadingIcon = Icons.Default.Policy) { }
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(text = "Feedback Form", leadingIcon = Icons.Default.Forum) { }
        }
    }
}

@Composable
fun UserDetailsCard(onMove: () -> Unit, imageUrl: String?) {
    // Card displaying user details
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User profile image
                CommonImage(
                    data = imageUrl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp)) // Space between image and text

                // User name and number
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

            Spacer(modifier = Modifier.height(16.dp)) // Space between user details and button

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
fun CustomButton(
    text: String,
    leadingIcon: ImageVector,
    onClick: () -> Unit
) {
    // Custom button with an icon and text
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(48.dp), // Button height
        contentPadding = PaddingValues(horizontal = 16.dp),
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
            // Leading icon
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp)) // Space between icon and text

            // Center text
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            // Trailing icon
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
