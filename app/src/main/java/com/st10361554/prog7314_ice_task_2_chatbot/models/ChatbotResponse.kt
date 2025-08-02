package com.st10361554.prog7314_ice_task_2_chatbot.models

import com.google.gson.annotations.SerializedName

data class ChatbotResponse(
    @SerializedName("text")
    val text: String,

    @SerializedName("citations")
    val citations: List<Citation>,

    @SerializedName("documentsUsed")
    val documentsUsed: Int,

    @SerializedName("categoriesReferenced")
    val categoriesReferenced: List<String>
)
