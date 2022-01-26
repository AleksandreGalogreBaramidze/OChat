package com.example.chat.ui.zezva

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.broadcast_reciever.BroadcastReceiver
import com.example.chat.models.MessagesModel
import com.example.chat.repository.MessagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ZezvaViewModel @Inject constructor(private val chatDaoRepository: MessagesRepository) : ViewModel() {
    var sendMessageLiveData = MutableLiveData<Intent>()
    var messagesLiveData = MutableLiveData<List<MessagesModel>>()
    private val def = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    fun sendMessage(sender: String, message: String) {
        val intent = Intent(BroadcastReceiver.SEND_MESSAGE).apply {
            putExtra(
                BroadcastReceiver.MESSAGE,
                MessagesModel(sender = sender, message = message, date = def.format(Calendar.getInstance().time))
            )
        }
        sendMessageLiveData.postValue(intent)
    }

    fun saveMessage(sender: String, message: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                chatDaoRepository.saveMessage(
                    MessagesModel(
                        sender = sender,
                        message = message,
                        date = def.format(Calendar.getInstance().time)
                    )
                )
            }
        }
    }

    fun setAllMessages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                messagesLiveData.postValue(chatDaoRepository.getMessages())
            }
        }
    }

    companion object{
        private const val DATE_FORMAT = "h:mm a"
    }
}