package com.st10361554.prog7314_ice_task_2_chatbot.chatbot

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.st10361554.prog7314_ice_task_2_chatbot.R
import com.st10361554.prog7314_ice_task_2_chatbot.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityChatBinding

    // View Components
    private lateinit var btnBack: Button
    private lateinit var btnClearChat: Button
    private lateinit var rvChatMessages: RecyclerView
    private lateinit var etPrompt: TextInputEditText
    private lateinit var fabSend: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

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

        setUpOnClickListeners()
    }

    private fun setUpOnClickListeners()
    {
        btnBack.setOnClickListener {
            finish()
        }

        btnClearChat.setOnClickListener {
            // Logic to clear chat messages
        }

        fabSend.setOnClickListener {
            // Logic to send the message from etPrompt
        }
    }
}