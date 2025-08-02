package com.st10361554.prog7314_ice_task_2_chatbot.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.st10361554.prog7314_ice_task_2_chatbot.R
import com.st10361554.prog7314_ice_task_2_chatbot.models.ChatMessage

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val messages = mutableListOf<ChatMessage>()

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    fun replaceLastMessage(message: ChatMessage) {
        if (messages.isNotEmpty()) {
            messages[messages.size - 1] = message
            notifyItemChanged(messages.size - 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearMessages() {
        messages.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardUserMessage: MaterialCardView = itemView.findViewById(R.id.cardUserMessage)
        private val cardAiMessage: MaterialCardView = itemView.findViewById(R.id.cardAiMessage)
        private val tvUserMessage: TextView = itemView.findViewById(R.id.tvUserMessage)
        private val tvAiMessage: TextView = itemView.findViewById(R.id.tvAiMessage)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarLoading)

        fun bind(message: ChatMessage) {
            if (message.isUser) {
                // Show user message
                cardUserMessage.visibility = View.VISIBLE
                cardAiMessage.visibility = View.GONE
                progressBar.visibility = View.GONE
                tvUserMessage.text = message.text
            } else if (message.isLoading) {
                // Show loading spinner
                cardUserMessage.visibility = View.GONE
                cardAiMessage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                // Show AI response
                cardUserMessage.visibility = View.GONE
                cardAiMessage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                tvAiMessage.text = message.text
            }
        }
    }
}