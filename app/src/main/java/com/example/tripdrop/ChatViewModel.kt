package com.example.tripdrop

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tripdrop.data.model.ChatData
import com.example.tripdrop.data.model.Message
import com.example.tripdrop.data.model.Event
import com.example.tripdrop.data.model.UserData
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    var db: FirebaseFirestore
) : ViewModel() {


    val inProcess = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val inProcessChats = mutableStateOf(false)
    val eventMutbaleState = mutableStateOf<Event<String>?>(null)
    val chats = mutableStateOf<List<ChatData>>(listOf())
    val chatMessages = mutableStateOf<List<Message>>(listOf())
    val inProcessMessages = mutableStateOf(false)
    var currentChatMessageListener: ListenerRegistration? = null


    fun displayMessages(chatId: String) {
        inProcessMessages.value = true
        currentChatMessageListener = db.collection(CHATS).document(chatId).collection(MESSAGES)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    chatMessages.value = value.documents.mapNotNull {
                        it.toObject<Message>()
                    }.sortedBy {
                        it.timestamp
                    }
                    inProcessMessages.value = false
                }
            }
    }

    fun hideMessage() {
        chatMessages.value = listOf()
        currentChatMessageListener = null
    }

    fun displayChats() {
        inProcessChats.value = true
        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.userId", userData.value?.userId),
                Filter.equalTo("user2.userId", userData.value?.userId)
            )
        ).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error)
            }
            if (value != null) {
                chats.value = value.documents.mapNotNull {
                    it.toObject<ChatData>()
                }
                inProcessChats.value = false
            }
        }
    }


    private fun getUserData(uid: String) {
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->

            if (error != null) {
                handleException(error, "Cannot Retrieve User")
            }

            if (value != null) {
                val user = value.toObject<UserData>()
                userData.value = user
                inProcess.value = false
                displayChats()
            }
        }
    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("Tag", "ChatterBox exception!", exception)
        exception?.printStackTrace()
        val errorMessage = exception?.localizedMessage ?: ""
        val message = customMessage.ifEmpty { errorMessage }

        eventMutbaleState.value = Event(message)
        inProcess.value = false
    }


    fun onSendReply(chatId: String, message: String) {
        val time = Calendar.getInstance().time.toString()
        val msg = userData.value?.userId?.let { Message(it, message, time) }
        if (msg != null) {
            db.collection(CHATS).document(chatId).collection(MESSAGES).document().set(msg)
        }
    }


}

