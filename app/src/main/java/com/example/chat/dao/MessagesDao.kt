package com.example.chat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chat.models.MessagesModel

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: MessagesModel)

    @Query("SELECT * FROM messages")
    fun getMessages(): List<MessagesModel>
}