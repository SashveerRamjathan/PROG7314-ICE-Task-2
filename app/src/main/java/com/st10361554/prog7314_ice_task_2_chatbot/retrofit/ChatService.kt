package com.st10361554.prog7314_ice_task_2_chatbot.retrofit

import com.st10361554.prog7314_ice_task_2_chatbot.models.ChatbotResponse
import com.st10361554.prog7314_ice_task_2_chatbot.models.UserPrompt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit service interface for communicating with the chatbot API.
 *
 * This interface defines the HTTP endpoints and expected request/response models
 * for interacting with the chatbot backend. It should be used with a Retrofit instance.
 */
interface ChatService {

    /**
     * Sends a user prompt to the chatbot API and receives a structured response.
     *
     * @param prompt The user's prompt or message to send to the chatbot.
     * @return [Response]<[ChatbotResponse]> containing the chatbot's reply and metadata.
     *
     * This function is marked as `suspend` for coroutine-based asynchronous calls.
     * The endpoint is a POST request to "/prompt" with the prompt as the request body.
     */
    @POST("/prompt")
    suspend fun sendMessage(@Body prompt: UserPrompt): Response<ChatbotResponse>
}