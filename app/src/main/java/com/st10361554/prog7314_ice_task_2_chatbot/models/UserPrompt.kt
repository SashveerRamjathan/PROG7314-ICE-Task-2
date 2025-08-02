package com.st10361554.prog7314_ice_task_2_chatbot.models

import com.google.gson.annotations.SerializedName

data class UserPrompt(
    @SerializedName("prompt")
    // The prompt is the user's input to the chatbot.
    // It is a string that contains the question or statement the user wants to ask.
    // This field is required for the chatbot to generate a response.
    val prompt: String
)
