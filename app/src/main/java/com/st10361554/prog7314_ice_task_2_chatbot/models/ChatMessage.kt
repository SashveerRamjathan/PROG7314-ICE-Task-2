package com.st10361554.prog7314_ice_task_2_chatbot.models

/**
 * Data class representing a single message in the chat conversation.
 *
 * This model is used for displaying messages in the chat UI, and helps distinguish between
 * user messages, chatbot responses, and loading states.
 *
 * @property text The actual message content (text) to be shown in the chat.
 * @property isUser True if the message was sent by the user; false if sent by the chatbot.
 * @property isLoading True if this message is a loading indicator (e.g., spinner while waiting for chatbot response).
 */
data class ChatMessage(
    val text: String,      // The message text shown in the chat bubble.
    val isUser: Boolean,   // True if the message is from the user, false if from the chatbot.
    val isLoading: Boolean = false // True if the message indicates a loading state.
)