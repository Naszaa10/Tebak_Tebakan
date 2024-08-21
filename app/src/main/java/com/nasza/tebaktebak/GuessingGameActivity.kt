package com.nasza.tebaktebak


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class GuessingGameActivity : AppCompatActivity() {

    private lateinit var flagImageView: ImageView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var statusImageView: ImageView
    private lateinit var errorImageView: ImageView
    private lateinit var correctFlag: Flag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessing_game)

        flagImageView = findViewById(R.id.flagImageView)
        answerEditText = findViewById(R.id.answerEditText)
        submitButton = findViewById(R.id.submitButton)
        statusImageView = findViewById(R.id.statusImageView)
        errorImageView = findViewById(R.id.errorImageView)

        val flag = intent.getParcelableExtra<Flag>("FLAG")

        if (flag != null) {
            correctFlag = flag
            flagImageView.setImageResource(flag.imageResId)
        } else {
            Toast.makeText(this, "No flag data found", Toast.LENGTH_SHORT).show()
            finish()
        }

        submitButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString().trim()
            checkAnswer(userAnswer)
        }
    }

    private fun checkAnswer(userAnswer: String) {
        if (userAnswer.equals(correctFlag.country, ignoreCase = true)) {
            correctFlag.isAnswered = true
            correctFlag.isCorrect = true.toString()
            statusImageView.visibility = View.VISIBLE
            errorImageView.visibility = View.GONE
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            correctFlag.isAnswered = true
            correctFlag.isCorrect = false.toString()
            statusImageView.visibility = View.GONE
            errorImageView.visibility = View.VISIBLE
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
        }
        val resultIntent = Intent()
        resultIntent.putExtra("FLAG", correctFlag)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
