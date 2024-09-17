package com.example.tripdrop.ui.presentation.home.details.chat

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.data.ChatMessage

@Composable
fun SingleChatScreen(
    vm: DropViewModel, senderId: String, receiverId: String, navController: NavController
) {

    val chatMessages by vm.chatMessages.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chat message list
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
//            items(chatMessages) { message ->
//                ChatMessageItem(message = message, isSender = message.senderId == senderId)
//            }
        }

        // Text input for sending a message
        var messageText by remember { mutableStateOf("") }

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                placeholder = { Text("Type a message") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Button(onClick = {
                if (messageText.isNotEmpty()) {
                    vm.sendMessage(senderId, receiverId, messageText)
                    messageText = "" // Clear input field after sending
                }
            }) {
                Text("Send")
            }
        }
    }


}

@Composable
fun ChatMessageItem(message: ChatMessage, isSender: Boolean) {
    val alignment = if (isSender) Alignment.End else Alignment.Start
    val backgroundColor = if (isSender) Color.BLUE else Color.GRAY
    val textColor = Color.WHITE

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
//            .align(alignment)
    ) {
//        Text(
//            text = message.message,
//            color = textColor,
////            modifier = Modifier.background(backgroundColor).padding(8.dp)
//        )
    }
}
