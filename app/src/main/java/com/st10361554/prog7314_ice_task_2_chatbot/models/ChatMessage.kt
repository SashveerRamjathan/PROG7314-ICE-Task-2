package com.st10361554.prog7314_ice_task_2_chatbot.models

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val isLoading: Boolean = false
)
