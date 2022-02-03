package com.example.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chat.models.Users
import com.example.chat.ui.zezva.ChatFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragments()
    }

    private fun setFragments() {
        with(supportFragmentManager) {
            beginTransaction().replace(R.id.firstFragment, ChatFragment(Users.OTHER_USER.ordinal.toString())).commit()
            beginTransaction().replace(R.id.secondFragment, ChatFragment(Users.CURRENT_USER.ordinal.toString())).commit()
        }
    }
}