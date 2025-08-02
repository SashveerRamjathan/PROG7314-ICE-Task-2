package com.st10361554.prog7314_ice_task_2_chatbot.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a prompt sent by the user to the chatbot.
 *
 * This class is used to serialize the user's input message for API requests.
 * The [prompt] property holds the text of the user's question or statement.
 *
 * @property prompt The user's message to the chatbot, used as input for generating a response.
 */
data class UserPrompt(
    @SerializedName("prompt")
    val prompt: String // The prompt is the user's input to the chatbot.
)