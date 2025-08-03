package com.st10361554.prog7314_ice_task_2_chatbot.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Utility object for creating and providing a Retrofit instance.
 *
 * This object simplifies the creation of Retrofit clients for making HTTP requests
 * to the chatbot API server. It uses Gson for JSON serialization/deserialization.
 *
 * You can customize request timeouts for slow or long-running API responses.
 */
object RetrofitUtils {

    /**
     * Creates and returns a [Retrofit] instance configured for the chatbot server.
     *
     * This configuration sets extended connection, read, and write timeouts
     * using OkHttpClient to handle slow or lengthy server responses.
     *
     * @return A [Retrofit] object with the base URL set to the chatbot API endpoint
     *         and using Gson for JSON conversion.
     *
     * Usage:
     * ```
     * val retrofit = RetrofitUtils.retrofit2()
     * val apiService = retrofit.create(ChatService::class.java)
     * ```
     */
    fun retrofit2(): Retrofit {
        // Configure OkHttpClient with extended timeouts
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(120, TimeUnit.SECONDS)   // Read (server response) timeout
            .writeTimeout(120, TimeUnit.SECONDS)  // Write (request sending) timeout
            .build()

        // Build and return a Retrofit instance with the specified base URL, OkHttpClient, and Gson converter
        return Retrofit.Builder()
            .baseUrl("https://prog-7314-cohere-chatbot-server.vercel.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}