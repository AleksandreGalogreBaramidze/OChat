package com.example.chat.di

import android.app.Application
import androidx.room.Room
import com.example.chat.dao.MessagesDao
import com.example.chat.dao.MessagesDatabase
import com.example.chat.repository.MessagesRepository
import com.example.chat.repository.MessagesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideMessagesDB(context: Application): MessagesDatabase {
        return Room.databaseBuilder(
            context,
            MessagesDatabase::class.java,
            "messages_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(messagesDatabase: MessagesDatabase): MessagesDao {
        return messagesDatabase.getChatDao()
    }

    @Provides
    @Singleton
    fun provideMessagesRepository(messagesDao: MessagesDao): MessagesRepository =
        MessagesRepositoryImpl(messagesDao)

}