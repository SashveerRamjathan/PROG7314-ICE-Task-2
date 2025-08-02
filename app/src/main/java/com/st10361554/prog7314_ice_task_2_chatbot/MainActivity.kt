package com.st10361554.prog7314_ice_task_2_chatbot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.st10361554.prog7314_ice_task_2_chatbot.chatbot.ChatActivity
import com.st10361554.prog7314_ice_task_2_chatbot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    // view components
    private lateinit var btnOpenChat: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpOnClickListeners()
    }

    private fun setUpOnClickListeners()
    {
        btnOpenChat = binding.btnOpenChat

        btnOpenChat.setOnClickListener {
            // Open the chat activity
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}