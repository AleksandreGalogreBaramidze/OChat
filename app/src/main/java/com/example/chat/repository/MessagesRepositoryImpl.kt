package com.example.chat.repository

import com.example.chat.dao.MessagesDao
import com.example.chat.models.MessagesModel
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(private val chatDao: MessagesDao) :
    MessagesRepository {
    override fun saveMessage(message: MessagesModel) {
        chatDao.saveMessage(message)
    }

    override fun getMessages(): List<MessagesModel> {
        return chatDao.getMessages()
    }
}