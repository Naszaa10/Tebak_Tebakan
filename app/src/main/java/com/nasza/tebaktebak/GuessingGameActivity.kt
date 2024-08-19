package com.nasza.tebaktebak

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuessingGameActivity : AppCompatActivity() {

    private lateinit var flagImageView: ImageView
    private lateinit var submitButton: Button
    private lateinit var levelTextView: TextView
    private lateinit var answerEditText: EditText
    private var currentLevel: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessing_game)

        // Get level from intent
        currentLevel = intent.getIntExtra("LEVEL", 1)

        // Initialize views
        flagImageView = findViewById(R.id.flagImageView)
        submitButton = findViewById(R.id.submitButton)
        levelTextView = findViewById(R.id.levelTextView)
        answerEditText = findViewById(R.id.answerEditText)

        // Set level text
        levelTextView.text = "Level $currentLevel"

        // Load images or logos based on level
        loadLevelData(currentLevel)

        // Handle submit button click
        submitButton.setOnClickListener {
            val answer = answerEditText.text.toString()
            checkAnswer(answer)
        }
    }

    private fun loadLevelData(level: Int) {
        when (level) {
            1 -> {
                // Load image for level 1
                flagImageView.setImageResource(R.drawable.brazil)  // Example image resource
            }
            2 -> {
                // Load image for level 2
                flagImageView.setImageResource(R.drawable.indonesia)
            }
            // Add more levels as needed
        }
    }

    private fun checkAnswer(answer: String) {
        // Logic to check the answer and provide feedback to the player
    }
}
