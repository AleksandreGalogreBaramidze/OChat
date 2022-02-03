package com.example.chat.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chat.models.MessagesModel

@Database(entities = [MessagesModel::class], version = 1)
abstract class MessagesDatabase : RoomDatabase() {
    abstract fun getChatDao(): MessagesDao
}