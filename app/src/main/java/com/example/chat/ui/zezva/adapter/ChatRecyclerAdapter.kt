package com.example.chat.ui.zezva.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.OtherUserMessageLayoutBinding
import com.example.chat.databinding.UserMessageLayoutBinding
import com.example.chat.models.MessagesModel

class ChatRecyclerAdapter (private val sender: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data: MutableList<MessagesModel> = mutableListOf()


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserMessageViewHolder -> holder.onBind(data[position])
            is OtherUserMessageViewHolder -> holder.onBind(data[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == USER_MESSAGE) {
            UserMessageViewHolder(
                UserMessageLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            OtherUserMessageViewHolder(
                OtherUserMessageLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        val message = data[position]
        val result: Int = if ( message.sender == sender) {
            USER_MESSAGE
        } else {
            OTHER_USER_MESSAGE
        }
        return result
    }

    class UserMessageViewHolder(private val binding: UserMessageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(message: MessagesModel) {
            with(binding) {
                userSentTextView.text = message.message
                userSentMessagesDateTextView.text = message.date
            }
        }
    }

    class OtherUserMessageViewHolder(private val binding: OtherUserMessageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(message: MessagesModel) {
            with(binding) {
                otherUserSentTextView.text = message.message
                otherUserSentMessagesDateTextView.text = message.date
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSavedMessages(messages:List<MessagesModel>){
        data.clear()
        data.addAll(messages)
        notifyDataSetChanged()
    }

    fun setMessage(message: MessagesModel) {
        this.data.add(message)
        notifyItemChanged(data.size - 1)
    }

    companion object {
        private const val USER_MESSAGE = 0
        private const val OTHER_USER_MESSAGE = 1
    }

}