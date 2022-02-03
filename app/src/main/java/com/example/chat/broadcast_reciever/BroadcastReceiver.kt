package com.example.chat.broadcast_reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.chat.models.MessagesModel

typealias getMessage = (isMessageSent: MessagesModel) -> Unit

class BroadcastReceiver : BroadcastReceiver() {
    lateinit var getMessage: getMessage
    override fun onReceive(context: Context?, intent: Intent?) {
        if (SEND_MESSAGE == intent?.action) {
            val message = intent.getParcelableExtra<MessagesModel>(MESSAGE)
            if (message != null) {
                getMessage.invoke(message)
            }
        }
    }

    companion object {
        const val SEND_MESSAGE = "sendMessage"
        const val MESSAGE = "message"
    }
}