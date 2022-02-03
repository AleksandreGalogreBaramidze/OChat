package com.example.chat.ui.zezva

import android.content.IntentFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.broadcast_reciever.BroadcastReceiver
import com.example.chat.databinding.ZezvaFragmentBinding
import com.example.chat.extensions.dataObserver
import com.example.chat.extensions.makeToast
import com.example.chat.ui.base.BaseFragment
import com.example.chat.ui.zezva.adapter.ChatRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment(private val sender: String) :
    BaseFragment<ZezvaFragmentBinding, ZezvaViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ZezvaFragmentBinding get() = ZezvaFragmentBinding::inflate
    override fun getViewModel() = ZezvaViewModel::class.java

    private lateinit var broadCastReceiver: BroadcastReceiver
    private lateinit var charRecyclerAdapter: ChatRecyclerAdapter
    override fun initFragment(viewModel: ZezvaViewModel) {
        broadCastReceiver = BroadcastReceiver()
        viewModel.setAllMessages()
        initRecycleView()
        setListeners(viewModel)
        setData(viewModel)
        broadCastReceiver.getMessage = {
            charRecyclerAdapter.setMessage(it)
        }
    }

    private fun setData(viewModel: ZezvaViewModel) {
        dataObserver(viewModel.sendMessageLiveData) {
            activity?.sendBroadcast(it)
            viewModel.saveMessage(sender, binding.userInputEditText.text.toString())
            binding.userInputEditText.text = null
        }

        dataObserver(viewModel.messagesLiveData) {
            charRecyclerAdapter.setSavedMessages(it)
        }
    }

    override fun onStart() {
        super.onStart()
        IntentFilter(BroadcastReceiver.SEND_MESSAGE).also {
            activity?.registerReceiver(broadCastReceiver, it)
        }
    }

    private fun setListeners(viewModel: ZezvaViewModel) {
        binding.messageSendButton.setOnClickListener {
            if (binding.userInputEditText.text.toString().isNotBlank()) {
                viewModel.sendMessage(sender, binding.userInputEditText.text.toString())
            } else {
                getString(R.string.fill_error).makeToast(requireContext())
            }

        }
    }


    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(broadCastReceiver)
    }

    private fun initRecycleView() {
        with(binding.messagesRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            charRecyclerAdapter = ChatRecyclerAdapter(sender)
            adapter = charRecyclerAdapter
        }
    }
}