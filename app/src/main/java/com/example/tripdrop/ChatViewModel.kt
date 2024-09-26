package com.example.tripdrop

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tripdrop.data.model.ChatData
import com.example.tripdrop.data.model.Message
import com.example.tripdrop.data.model.Event
import com.example.tripdrop.data.model.UserData
import com.example.tripdrop.ui.presentation.common.CHATS
import com.example.tripdrop.ui.presentation.common.MESSAGES
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

/**
 * ViewModel for the chat functionality in the TripDrop app.
 * Handles the state and logic for displaying chat messages, sending replies, and managing the chat data.
 *
 * @property db the FirebaseFire store instance used to interact with the Fire store database
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    private val inProcess = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    private val inProcessChats = mutableStateOf(false)
    private val eventState = mutableStateOf<Event<String>?>(null)
    val chats = mutableStateOf<List<ChatData>>(listOf())
    val chatMessages = mutableStateOf<List<Message>>(listOf())
    private val inProcessMessages = mutableStateOf(false)
    private var currentChatMessageListener: ListenerRegistration? = null

    private val chatsRef get() = db.collection(CHATS)

    fun displayMessages(chatId: String) {
        inProcessMessages.value = true
        currentChatMessageListener = chatsRef.document(chatId).collection(MESSAGES)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error)
                    return@addSnapshotListener
                }
                value?.let { snapshot ->
                    chatMessages.value = snapshot.documents.mapNotNull { it.toObject<Message>() }
                        .sortedBy { it.timestamp }
                    inProcessMessages.value = false
                }
            }
    }

    fun hideMessages() {
        chatMessages.value = listOf()
        currentChatMessageListener = null
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("ChatViewModel", "Exception in ChatViewModel", exception)
        exception?.printStackTrace()

        val errorMessage = exception?.localizedMessage ?: ""
        val message = customMessage.ifEmpty { errorMessage }
        eventState.value = Event(message)
        inProcess.value = false
    }

    fun onSendReply(chatId: String, message: String) {
        userData.value?.userId?.let { userId ->
            val time = Calendar.getInstance().time.toString()
            val msg = Message(userId, message, time)
            chatsRef.document(chatId).collection(MESSAGES).document().set(msg)
        }
    }

    fun getOrCreateChat(user1Id: String, user2Id: String, onChatIdReceived: (String?) -> Unit) {
        inProcessChats.value = true

        // Query for an existing chat
        chatsRef.where(
            Filter.or(
                Filter.and(
                    Filter.equalTo("user1.userId", user1Id),
                    Filter.equalTo("user2.userId", user2Id)
                ),
                Filter.and(
                    Filter.equalTo("user1.userId", user2Id),
                    Filter.equalTo("user2.userId", user1Id)
                )
            )
        ).get().addOnSuccessListener { querySnapshot ->
            querySnapshot.documents.firstOrNull()?.toObject<ChatData>()?.let { existingChat ->
                onChatIdReceived(existingChat.chatId)  // Existing chat found
            } ?: run {
                // Create a new chat if not found
                val newChatId = chatsRef.document().id
                val newChat = ChatData(
                    chatId = newChatId,
                    user1 = UserData(userId = user1Id),
                    user2 = UserData(userId = user2Id)
                )
                chatsRef.document(newChatId).set(newChat)
                    .addOnSuccessListener { onChatIdReceived(newChatId) }
                    .addOnFailureListener { exception ->
                        handleException(exception, "Failed to create new chat")
                        onChatIdReceived(null)
                    }
            }
        }.addOnFailureListener { exception ->
            handleException(exception, "Failed to retrieve chat")
            onChatIdReceived(null)
        }.addOnCompleteListener {
            inProcessChats.value = false
        }
    }
}
