package com.example.tripdrop.ui.presentation.home.details.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tripdrop.viewModel.ChatViewModel
import com.example.tripdrop.R
import com.example.tripdrop.data.model.Message
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.common.CommonImage


@Composable
fun SingleChatScreen(navController: NavController, chatModel: ChatViewModel, chatId: String) {

    var reply by rememberSaveable {
        mutableStateOf("")
    }

    val onSendReply = {
        chatModel.onSendReply(chatId, reply)
        reply = ""
    }

    val myUser = chatModel.userData.value

    // Use firstOrNull to prevent crashes when no matching chat is found
    val currentChat = chatModel.chats.value.firstOrNull { it.chatId == chatId }

    // If currentChat is null, handle the error by returning or showing an error message
    if (currentChat == null) {
        // You can handle it by showing a default message or navigate back
        LaunchedEffect(Unit) {
            navController.popBackStack() // Optional, navigate back if chat not found
        }
        return // Prevent further execution if no chat is found
    }

    // Determine the chatUser based on the current user
    val chatUser =
        if (myUser?.userId == currentChat.user1.userId) currentChat.user2 else currentChat.user1

    val chatMsg by chatModel.chatMessages

    // Load the chat messages when the chatId changes
    LaunchedEffect(key1 = chatId) {
        chatModel.displayMessages(chatId)
    }

    // Handle back button press to navigate to the previous screen
    BackHandler {
        navController.navigate(Route.ProductDetailScreen.name)
    }

    // UI layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(Color.White)
    ) {
        TopChatBar(name = chatUser.name ?: "", imageUrl = chatUser.imageUrl ?: "") {
            navController.popBackStack()
            chatModel.hideMessages()
        }
        MessageBox(
            modifier = Modifier.weight(1f),
            chatMessage = chatMsg,
            currentUserId = myUser?.userId ?: ""
        )
        ReplyBox(reply = reply, onReplyChange = { reply = it }, onSendReply = onSendReply)
    }
}



@Composable
fun MessageBox(modifier: Modifier, chatMessage: List<Message>, currentUserId: String) {
    val listState = rememberLazyListState()

    LaunchedEffect(chatMessage) {
        if (chatMessage.isNotEmpty()) {
            listState.animateScrollToItem(chatMessage.size - 1)
        }
    }

    LazyColumn(modifier = modifier, state = listState) {
        items(chatMessage) { msg ->
            val alignment = if (msg.sendBy == currentUserId) Alignment.End else Alignment.Start
            val color =
                if (msg.sendBy == currentUserId) colorResource(id = R.color.black) else colorResource(
                    id = R.color.black
                )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = alignment
            ) {
                Text(
                    text = msg.message ?: "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color)
                        .padding(12.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopChatBar(name: String, imageUrl: String, onBackClicked: () -> Unit) {
    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickable {
                        onBackClicked.invoke()
                    },
                tint = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.width(22.dp))
            CommonImage(
                data = imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        colorResource(id = R.color.black)
                    )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = name,
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
                tint = Color.White
            )
        }
        IconButton(onClick = {

        }) {
            Icon(
                Icons.Default.VideoCall,
                contentDescription = "Localized description",
                tint = Color.White
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.black))
    )
}

@Composable
fun ReplyBox(
    reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(value = reply,
                onValueChange = onReplyChange,
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
                    IconButton(onClick = onSendReply) {
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


fun Modifier.senderBarBorder() = composed {
    if (!isSystemInDarkTheme() || isSystemInDarkTheme()) {
        border(
            width = 1.dp,
            color = colorResource(id = (R.color.black)),
            shape = MaterialTheme.shapes.medium
        )
    } else {
        this
    }
}
