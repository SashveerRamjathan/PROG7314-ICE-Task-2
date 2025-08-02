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

/**
 * MainActivity is the entry point of the application.
 * It displays the main screen and provides navigation to the chatbot interface.
 *
 * @property binding The view binding for the activity's layout.
 * @property btnOpenChat The button used to open the chat activity.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // View components
    private lateinit var btnOpenChat: Button

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables edge-to-edge display for a more immersive experience
        enableEdgeToEdge()

        // Inflate the view binding for the main activity layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply system window insets to the root view so content doesn't overlap system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up click listeners for UI components
        setUpOnClickListeners()
    }

    /**
     * Sets up click listeners for buttons and other interactive UI elements.
     * Specifically, this initializes the button that opens the chat activity.
     */
    private fun setUpOnClickListeners() {
        btnOpenChat = binding.btnOpenChat

        // Set listener to open ChatActivity when the button is clicked
        btnOpenChat.setOnClickListener {
            // Create an intent to navigate to the chat activity
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}