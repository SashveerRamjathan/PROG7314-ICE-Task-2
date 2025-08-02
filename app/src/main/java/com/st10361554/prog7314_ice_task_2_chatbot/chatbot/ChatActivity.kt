package com.st10361554.prog7314_ice_task_2_chatbot.chatbot

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.st10361554.prog7314_ice_task_2_chatbot.adapters.ChatAdapter
import com.st10361554.prog7314_ice_task_2_chatbot.databinding.ActivityChatBinding
import com.st10361554.prog7314_ice_task_2_chatbot.models.ChatMessage
import com.st10361554.prog7314_ice_task_2_chatbot.models.ChatbotResponse
import com.st10361554.prog7314_ice_task_2_chatbot.models.UserPrompt
import com.st10361554.prog7314_ice_task_2_chatbot.retrofit.ChatService
import com.st10361554.prog7314_ice_task_2_chatbot.retrofit.RetrofitUtils
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    // View Components
    private lateinit var btnBack: Button
    private lateinit var btnClearChat: Button
    private lateinit var rvChatMessages: RecyclerView
    private lateinit var etPrompt: TextInputEditText
    private lateinit var fabSend: FloatingActionButton

    private lateinit var chatService: ChatService
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force adjust resize mode
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize view components from the binding
        btnBack = binding.btnBack
        btnClearChat = binding.btnClearChat
        rvChatMessages = binding.recyclerViewChat
        etPrompt = binding.etPrompt
        fabSend = binding.fabSend

        // Initialize the chat service
        chatService = RetrofitUtils.retrofit2().create(ChatService::class.java)

        setupRecyclerView()
        setUpOnClickListeners()
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        rvChatMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }
    }

    private fun setUpOnClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnClearChat.setOnClickListener {
            chatAdapter.clearMessages()
        }

        fabSend.setOnClickListener {
            val messageText = etPrompt.text?.toString()?.trim()
            if (!messageText.isNullOrEmpty()) {
                sendMessageToAPI(messageText)
                etPrompt.text?.clear()
            }
        }

        // Handle Enter key press in EditText
        etPrompt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
                fabSend.performClick()
                true
            } else {
                false
            }
        }
    }

    private fun sendMessageToAPI(messageText: String) {
        // Add user message to chat
        chatAdapter.addMessage(ChatMessage(messageText, isUser = true))

        // Add loading spinner
        chatAdapter.addMessage(ChatMessage("", isUser = false, isLoading = true))

        // Scroll to bottom
        rvChatMessages.scrollToPosition(chatAdapter.itemCount - 1)

        // Disable send button during API call
        fabSend.isEnabled = false

        lifecycleScope.launch {
            try {
                val userPrompt = UserPrompt(messageText) // Adjust based on your UserPrompt model
                val response = sendMessage(userPrompt)

                // Replace loading spinner with actual response
                chatAdapter.replaceLastMessage(
                    ChatMessage(response.text, isUser = false, isLoading = false)
                )

            } catch (e: Exception) {
                // Replace loading spinner with error message
                chatAdapter.replaceLastMessage(
                    ChatMessage("Sorry, I encountered an error: ${e.message}", isUser = false, isLoading = false)
                )

                Toast.makeText(this@ChatActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                // Re-enable send button
                fabSend.isEnabled = true

                // Scroll to bottom
                rvChatMessages.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    private suspend fun sendMessage(prompt: UserPrompt): ChatbotResponse {
        try {
            val response = chatService.sendMessage(prompt)

            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            } else {
                throw Exception("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            val errorMessage = "Error: ${e.message}"
            throw Exception(errorMessage)
        }
    }
}