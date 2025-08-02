package com.st10361554.prog7314_ice_task_2_chatbot.models

import com.google.gson.annotations.SerializedName

data class Citation(
    @SerializedName("start")
    val start: Int,

    @SerializedName("end")
    val end: Int,

    @SerializedName("text")
    val text: String,

    @SerializedName("documentIds")
    val documentIds: List<String>,

    @SerializedName("type")
    val type: String

)
