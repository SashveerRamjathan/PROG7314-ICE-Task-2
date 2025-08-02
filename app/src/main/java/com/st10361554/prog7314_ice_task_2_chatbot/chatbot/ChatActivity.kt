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

/**
 * ChatActivity is the main UI for the chatbot conversation.
 *
 * Handles sending user messages to the chatbot API, displaying conversation history,
 * and managing user interactions (sending, clearing, and navigating back).
 *
 * @property binding View binding for activity layout.
 * @property btnBack Button to go back to previous activity.
 * @property btnClearChat Button to clear chat history.
 * @property rvChatMessages RecyclerView showing chat messages.
 * @property etPrompt Input field for user to type messages.
 * @property fabSend Button to send user message.
 * @property chatService Retrofit service for API calls.
 * @property chatAdapter Adapter to manage chat messages in RecyclerView.
 */
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    // View Components
    private lateinit var btnBack: Button
    private lateinit var btnClearChat: Button
    private lateinit var rvChatMessages: RecyclerView
    private lateinit var etPrompt: TextInputEditText
    private lateinit var fabSend: FloatingActionButton

    // Retrofit chat service instance
    private lateinit var chatService: ChatService
    // Adapter for RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    /**
     * Called when the activity is first created.
     * Sets up the UI, initializes view components, chat service, RecyclerView, and event listeners.
     *
     * @param savedInstanceState Bundle for restoring state if activity is recreated.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure window resizes properly when keyboard appears
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)

        // Inflate view binding
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply system window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize view components from binding
        btnBack = binding.btnBack
        btnClearChat = binding.btnClearChat
        rvChatMessages = binding.recyclerViewChat
        etPrompt = binding.etPrompt
        fabSend = binding.fabSend

        // Initialize Retrofit chat service
        chatService = RetrofitUtils.retrofit2().create(ChatService::class.java)

        // Initialize RecyclerView and its adapter
        setupRecyclerView()
        // Setup event listeners for buttons and input
        setUpOnClickListeners()
    }

    /**
     * Sets up the RecyclerView for chat messages and attaches the ChatAdapter.
     */
    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        rvChatMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }
    }

    /**
     * Sets up click listeners for user interaction buttons and input field.
     * Handles navigation, clearing chat, sending messages, and keyboard actions.
     */
    private fun setUpOnClickListeners() {
        // Navigates back to previous activity
        btnBack.setOnClickListener {
            finish()
        }

        // Clears all chat messages from the RecyclerView
        btnClearChat.setOnClickListener {
            chatAdapter.clearMessages()
        }

        // Sends user message when send button is clicked
        fabSend.setOnClickListener {
            val messageText = etPrompt.text?.toString()?.trim()
            if (!messageText.isNullOrEmpty()) {
                sendMessageToAPI(messageText)
                etPrompt.text?.clear()
            }
        }

        // Allows sending message with the keyboard's send action
        etPrompt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
                fabSend.performClick()
                true
            } else {
                false
            }
        }
    }

    /**
     * Sends a message to the chatbot API and updates the RecyclerView with response.
     * Shows a loading spinner while waiting for API response and handles errors gracefully.
     *
     * @param messageText The user's message to send to the chatbot.
     */
    private fun sendMessageToAPI(messageText: String) {
        // Add user's message to chat history
        chatAdapter.addMessage(ChatMessage(messageText, isUser = true))

        // Show loading spinner while waiting for chatbot response
        chatAdapter.addMessage(ChatMessage("", isUser = false, isLoading = true))

        // Scroll to bottom to show latest message
        rvChatMessages.scrollToPosition(chatAdapter.itemCount - 1)

        // Disable send button to prevent multiple submissions
        fabSend.isEnabled = false

        // Launch coroutine for async API call
        lifecycleScope.launch {
            try {
                // Prepare user prompt model for API
                val userPrompt = UserPrompt(messageText)
                // Send message to API and get response
                val response = sendMessage(userPrompt)

                // Replace loading spinner with chatbot's response
                chatAdapter.replaceLastMessage(
                    ChatMessage(response.text, isUser = false, isLoading = false)
                )
            } catch (e: Exception) {
                // Replace loading spinner with an error message
                chatAdapter.replaceLastMessage(
                    ChatMessage("Sorry, I encountered an error: ${e.message}", isUser = false, isLoading = false)
                )
                // Show error as toast notification
                Toast.makeText(this@ChatActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                // Re-enable send button after API call finishes
                fabSend.isEnabled = true

                // Ensure chat scrolls to bottom
                rvChatMessages.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    /**
     * Suspends and sends the user's prompt to the chatbot API using Retrofit.
     *
     * @param prompt The [UserPrompt] containing user's message.
     * @return [ChatbotResponse] The chatbot's response object.
     * @throws Exception if the API response is not successful or body is null.
     */
    private suspend fun sendMessage(prompt: UserPrompt): ChatbotResponse {
        try {
            // Make network request to chatbot API
            val response = chatService.sendMessage(prompt)

            // Check if response is successful and has a body
            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            } else {
                // Throw exception if API error or missing response body
                throw Exception("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            // Wrap and throw any caught exceptions with error message
            val errorMessage = "Error: ${e.message}"
            throw Exception(errorMessage)
        }
    }
}