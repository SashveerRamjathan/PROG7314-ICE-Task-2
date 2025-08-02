package com.st10361554.prog7314_ice_task_2_chatbot.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {

    fun retrofit2(): Retrofit {
        return Retrofit.Builder().baseUrl("https://prog-7314-cohere-chatbot-server.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}