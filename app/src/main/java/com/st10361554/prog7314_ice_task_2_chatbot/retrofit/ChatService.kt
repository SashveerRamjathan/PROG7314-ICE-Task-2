package com.st10361554.prog7314_ice_task_2_chatbot.retrofit

import com.st10361554.prog7314_ice_task_2_chatbot.models.ChatbotResponse
import com.st10361554.prog7314_ice_task_2_chatbot.models.UserPrompt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService
{
    @POST("/prompt")
    suspend fun sendMessage(@Body prompt: UserPrompt): Response<ChatbotResponse>
}