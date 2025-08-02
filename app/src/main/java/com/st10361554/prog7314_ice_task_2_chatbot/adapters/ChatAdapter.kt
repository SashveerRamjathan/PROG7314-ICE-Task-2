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

/**
 * RecyclerView Adapter for displaying chat messages in the conversation UI.
 *
 * This adapter manages the list of chat messages, supports adding messages,
 * replacing the last message (useful for updating loading indicators to responses),
 * and clearing the chat history.
 *
 * It supports three view types:
 * 1. User messages
 * 2. AI responses
 * 3. Loading spinner (while waiting for API response)
 */
class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // List holding all messages in the chat conversation.
    private val messages = mutableListOf<ChatMessage>()

    /**
     * Adds a new message to the chat and updates the RecyclerView.
     *
     * @param message The [ChatMessage] to add.
     */
    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    /**
     * Replaces the last message in the chat with a new message.
     * Typically used to replace a loading spinner with the actual response.
     *
     * @param message The [ChatMessage] to replace the last one.
     */
    fun replaceLastMessage(message: ChatMessage) {
        if (messages.isNotEmpty()) {
            messages[messages.size - 1] = message
            notifyItemChanged(messages.size - 1)
        }
    }

    /**
     * Clears all chat messages from the adapter and updates the RecyclerView.
     * Uses [NotifyDataSetChanged] since all items are removed.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun clearMessages() {
        messages.clear()
        notifyDataSetChanged()
    }

    /**
     * Inflates the chat message item view and creates a [ChatViewHolder].
     *
     * @param parent The parent view group.
     * @param viewType The view type of the new View.
     * @return A new [ChatViewHolder] instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    /**
     * Binds the chat message data to the ViewHolder.
     *
     * @param holder The [ChatViewHolder].
     * @param position The position in the list.
     */
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    /**
     * Returns the total number of chat messages.
     *
     * @return The size of the messages list.
     */
    override fun getItemCount(): Int = messages.size

    /**
     * ViewHolder class for chat message items.
     * Handles binding different message types (user, AI, loading) to the UI.
     *
     * @property cardUserMessage Card view for user message.
     * @property cardAiMessage Card view for AI response.
     * @property tvUserMessage TextView for user message text.
     * @property tvAiMessage TextView for AI response text.
     * @property progressBar ProgressBar for loading spinner.
     */
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardUserMessage: MaterialCardView = itemView.findViewById(R.id.cardUserMessage)
        private val cardAiMessage: MaterialCardView = itemView.findViewById(R.id.cardAiMessage)
        private val tvUserMessage: TextView = itemView.findViewById(R.id.tvUserMessage)
        private val tvAiMessage: TextView = itemView.findViewById(R.id.tvAiMessage)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarLoading)

        /**
         * Binds a [ChatMessage] to the UI, showing the appropriate view.
         *
         * @param message The [ChatMessage] to display.
         */
        fun bind(message: ChatMessage) {
            if (message.isUser) {
                // Show user message card, hide others
                cardUserMessage.visibility = View.VISIBLE
                cardAiMessage.visibility = View.GONE
                progressBar.visibility = View.GONE
                tvUserMessage.text = message.text
            } else if (message.isLoading) {
                // Show loading spinner only
                cardUserMessage.visibility = View.GONE
                cardAiMessage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                // Show AI response card
                cardUserMessage.visibility = View.GONE
                cardAiMessage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                tvAiMessage.text = message.text
            }
        }
    }
}