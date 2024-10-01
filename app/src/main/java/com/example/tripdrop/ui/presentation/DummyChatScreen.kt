package com.example.tripdrop.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tripdrop.R
import com.example.tripdrop.ui.presentation.home.details.chat.senderBarBorder

@Composable
fun DummyChatScreen(){
    Column (
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ){
        TopChatBarDummy()
        MessageBoxDummy()
        ReplyBoxDummy()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopChatBarDummy() {
    TopAppBar(title = {
        Row(
            modifier = Modifier.padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickable {

                    },
                tint = colorResource(id = R.color.black)
            )
            Spacer(modifier = Modifier.width(22.dp))
            Text(
                text = "",
                color = colorResource(id = R.color.white),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }, actions = {
        IconButton(onClick = {

        }) {
            Icon(
                Icons.Default.Phone,
                contentDescription = "Localized description",
                tint = Color.Black
            )
        }
        IconButton(onClick = {

        }) {
            Icon(
                Icons.Default.VideoCall,
                contentDescription = "Localized description",
                tint = Color.Black
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.white)),
        modifier = Modifier.height(90.dp).padding(top = 30.dp)
    )
}

@Composable
fun ReplyBoxDummy() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(5.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(value = "",
                onValueChange = { },
                placeholder = {
                    Text(text = "Type something .....")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp)
                    .senderBarBorder(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.white),
                    focusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = null,
                            tint = colorResource(id = (R.color.black)),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = null,
                            tint = colorResource(id = (R.color.black)),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                })
        }
    }
}

@Composable
fun MessageBoxDummy() {
    Column(modifier = Modifier.fillMaxWidth().height(720.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimationChatting()
    }
}

@Composable
fun LottieAnimationChatting() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chatting))
    val progress by animateLottieCompositionAsState(
        composition = composition, restartOnPlay = true, iterations = LottieConstants.IterateForever
    )

    LottieAnimation(modifier = Modifier.size(300.dp),
        composition = composition,
        progress = { progress })

}

@Preview
@Composable
fun ChattingScreenPreview() {
    DummyChatScreen()
}