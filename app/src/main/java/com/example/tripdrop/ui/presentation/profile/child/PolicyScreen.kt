package com.example.tripdrop.ui.presentation.profile.child

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Privacy Policy",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                item { PolicyScreenSectionTitle(title = "1. Introduction") }
                item {
                    PolicyScreenParagraphText(
                        text = "Welcome to Dropit. Your privacy is of paramount importance to us, and we are committed to protecting the personal information you share with us. This Privacy Policy outlines how we collect, use, and safeguard your data when using our app."
                    )
                }

                item { PolicyScreenSectionTitle(title = "2. Information We Collect") }
                item {
                    PolicyScreenParagraphText(
                        text = "We may collect the following types of information:"
                    )
                }
                item { SubSectionTitle(title = "Personal Information:") }
                item {
                    PolicyScreenParagraphText(
                        text = "Name, email address, phone number, and any other information you provide when creating an account."
                    )
                }
                item { SubSectionTitle(title = "Usage Data:") }
                item {
                    PolicyScreenParagraphText(
                        text = "Information about your device and how you interact with our app, including your IP address, device type, and usage patterns."
                    )
                }
                item { SubSectionTitle(title = "Location Data:") }
                item {
                    PolicyScreenParagraphText(
                        text = "If you allow, we may collect location information to provide enhanced services."
                    )
                }

                item { PolicyScreenSectionTitle(title = "3. How We Use Your Information") }
                item {
                    PolicyScreenParagraphText(
                        text = "We use your data to:"
                    )
                }
                item {
                    PolicyScreenBulletPointText(text = "Provide and improve our app's functionality.")
                }
                item {
                    PolicyScreenBulletPointText(text = "Personalize your experience and recommend relevant content.")
                }
                item {
                    PolicyScreenBulletPointText(text = "Communicate with you about updates, promotions, and other relevant information.")
                }
                item {
                    PolicyScreenBulletPointText(text = "Protect against fraud and ensure the security of our app.")
                }

                item { PolicyScreenSectionTitle(title = "4. Data Sharing") }
                item {
                    PolicyScreenParagraphText(
                        text = "We do not sell or rent your personal information. However, we may share your data with:"
                    )
                }
                item {
                    PolicyScreenBulletPointText(text = "Service Providers: To help us operate and maintain our app.")
                }
                item {
                    PolicyScreenBulletPointText(text = "Legal Authorities: If required by law or to protect the rights, property, or safety of our users or the public.")
                }

                item { SectionTitle(title = "5. Data Security") }
                item {
                    PolicyScreenParagraphText(
                        text = "We use industry-standard security measures to protect your data from unauthorized access, alteration, or disclosure. However, no system is 100% secure, and we cannot guarantee the absolute security of your information."
                    )
                }

                item { PolicyScreenSectionTitle(title = "6. Your Rights") }
                item {
                    PolicyScreenParagraphText(
                        text = "You have the right to:"
                    )
                }
                item {
                    PolicyScreenBulletPointText(text = "Access the personal information we hold about you.")
                }
                item {
                    PolicyScreenBulletPointText(text = "Request the correction or deletion of your data.")
                }
                item {
                    PolicyScreenBulletPointText(text = "Opt-out of marketing communications at any time.")
                }

                item { PolicyScreenSectionTitle(title = "7. Changes to This Policy") }
                item {
                    PolicyScreenParagraphText(
                        text = "We may update this policy from time to time. Any changes will be posted in the app, and we encourage you to review it periodically."
                    )
                }

                item { PolicyScreenSectionTitle(title = "8. Contact Us") }
                item {
                    PolicyScreenParagraphText(
                        text = "If you have any questions or concerns about this policy, please contact us at help.dropit@gmail.com."
                    )
                }
            }
        }
    }
}


@Composable
fun PolicyScreenSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp),
        color = Color.Black // Black text for section titles
    )
}

@Composable
fun SubSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp),
        color = Color.Black // Black text for subsection titles
    )
}

@Composable
fun PolicyScreenParagraphText(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = Modifier.padding(vertical = 4.dp),
        color = Color.Gray // Gray text for paragraphs
    )
}

@Composable
fun PolicyScreenBulletPointText(text: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(text = "• ", fontSize = 14.sp, color = Color.Black) // Black bullet point
        Text(text = text, fontSize = 14.sp, color = Color.Gray) // Gray text for bullet point content
    }
}

@Preview
@Composable
fun PolicyScreenPreview() {
    PolicyScreen()
}