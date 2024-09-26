package com.example.tripdrop.ui.presentation.profile.child

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.tripdrop.R


@Composable
fun HelpScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Help & Support",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 8.dp),
                color = colorResource(id = R.color.black),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            FAQScreen()
        }
    }
}

@Composable
fun FAQScreen() {
    val faqList = listOf(
        "How do I create an account?" to "To create an account, follow these steps:\n\n1. Open the app and tap on the \"Sign Up\" button.\n2. Enter your email address, create a password, and provide other required information.\n3. Once you’ve filled in the details, tap \"Submit.\" You’ll receive a confirmation email to verify your account.",
        "I forgot my password, how can I reset it?" to "If you forget your password:\n\n1. Tap on the \"Forgot Password\" link on the login page.\n2. Enter the email associated with your account.\n3. Follow the instructions sent to your email to reset your password.",
        "How do I update my profile information?" to "To update your profile:\n\n1. Navigate to your profile by tapping on your profile icon in the top right corner.\n2. Select \"Edit Profile.\"\n3. Update your name, contact information, or profile picture, and save your changes.",
        "How do I change my notification preferences?" to "You can customize your notifications by:\n\n1. Going to the \"Settings\" section of the app.\n2. Tap \"Notifications.\"\n3. Choose which notifications you’d like to enable or disable (e.g., push notifications, email alerts).",
        "How do I contact customer support?" to "If you need further assistance:\n\n1. Go to the \"Help\" or \"Contact Us\" section.\n2. You can either send an email to our support team or reach out via the in-app chat feature if available.\n3. Our support team will get back to you as soon as possible.",
        "Why am I not receiving push notifications?" to "If you're not receiving notifications:\n\n1. Ensure you have enabled push notifications in both the app and your device settings.\n2. If notifications are still not working, try logging out and back into your account.\n3. You may also need to reinstall the app to resolve the issue.",
        "How do I delete my account?" to "If you wish to delete your account:\n\n1. Navigate to the \"Settings\" section of the app.\n2. Tap on \"Account\" and select \"Delete Account.\"\n3. Please note that once your account is deleted, it cannot be recovered, and all your data will be permanently erased.",
        "How can I report a bug or provide feedback?" to "We appreciate your feedback! To report a bug or share your suggestions:\n\n1. Go to the \"Feedback\" section in the app.\n2. Fill out the form with details about the issue or your suggestion.\n3. Alternatively, you can email us directly at support@appname.com.",
        "Can I use the app offline?" to "Some features of the app may work offline, but full functionality (such as data sync or real-time updates) requires an active internet connection.",
        "How do I log out of the app?" to "To log out:\n\n1. Tap on your profile icon or navigate to the \"Settings\" menu.\n2. Scroll down and tap \"Log Out.\"\n3. You will be signed out of your account, but you can log back in anytime."
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(faqList.size) { index ->
            FAQItem(question = faqList[index].first, answer = faqList[index].second)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = question,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Text(
                    text = answer,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFAQScreen() {
    HelpScreen()
}
