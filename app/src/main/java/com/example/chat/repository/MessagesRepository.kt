package com.example.chat.repository

import com.example.chat.models.MessagesModel


interface MessagesRepository {
    fun saveMessage(message: MessagesModel)
    fun getMessages(): List<MessagesModel>
}