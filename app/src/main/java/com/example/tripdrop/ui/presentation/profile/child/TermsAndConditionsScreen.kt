package com.example.tripdrop.ui.presentation.profile.child

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen() {
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 30.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Terms & Conditions",
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
    ) { paddingValues ->
        // Wrap the LazyColumn inside a Box with a white background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Set the entire background color to white, including the padding area
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp) // Additional padding
                    .fillMaxSize()
            ) {
                item { SectionTitle(title = "1. Acceptance of Terms") }
                item {
                    ParagraphText(
                        text = "By using Dropit, you agree to these terms and conditions. If you do not agree with any part of these terms, you must discontinue using the app immediately."
                    )
                }

                item { SectionTitle(title = "2. Use of the App") }
                item { BulletPointText(text = "You must be at least 18+ to use this app.") }
                item { BulletPointText(text = "You agree not to misuse the app or interfere with its operation. This includes refraining from hacking, spamming, or distributing malware.") }
                item { BulletPointText(text = "We reserve the right to suspend or terminate your access to the app if you violate these terms.") }

                item { SectionTitle(title = "3. Content and Intellectual Property") }
                item {
                    ParagraphText(
                        text = "All content and materials provided in the app, including images, text, logos, and software, are the property of Dropit or its licensors."
                    )
                }
                item {
                    ParagraphText(
                        text = "You may not copy, modify, or distribute any part of the app without our explicit permission."
                    )
                }

                item { SectionTitle(title = "4. User-Generated Content") }
                item {
                    ParagraphText(
                        text = "If your app allows users to post or share content:"
                    )
                }
                item { BulletPointText(text = "You are responsible for the content you post in the app.") }
                item { BulletPointText(text = "By posting, you grant us a license to use, modify, and distribute your content as necessary for app functionality.") }
                item { BulletPointText(text = "We reserve the right to remove any content that violates these terms or is deemed inappropriate.") }

                item { SectionTitle(title = "5. Disclaimer") }
                item {
                    ParagraphText(
                        text = "The app and its services are provided \"as is\" without any warranties, express or implied. We do not guarantee the accuracy or reliability of the information provided within the app."
                    )
                }

                item { SectionTitle(title = "6. Limitation of Liability") }
                item {
                    ParagraphText(
                        text = "We are not responsible for any damages or losses resulting from your use of the app, including but not limited to data loss, technical issues, or unauthorized access to your account."
                    )
                }

                item { SectionTitle(title = "7. Changes to These Terms") }
                item {
                    ParagraphText(
                        text = "We may update these terms at any time. Continued use of the app after changes are posted constitutes your acceptance of the revised terms."
                    )
                }

                item { SectionTitle(title = "8. Governing Law") }
                item {
                    ParagraphText(
                        text = "These terms are governed by the laws of India, and any disputes will be resolved in the courts of India."
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp),
        color = Color.Black
    )
}

@Composable
fun ParagraphText(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = Modifier.padding(vertical = 4.dp),
        color = Color.Black
    )
}

@Composable
fun BulletPointText(text: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(text = "• ", fontSize = 14.sp, color = Color.Black)
        Text(text = text, fontSize = 14.sp, color = Color.Black)
    }
}


@Preview
@Composable
fun TermsAndConditionsScreenPreview() {
    TermsAndConditionsScreen()
}