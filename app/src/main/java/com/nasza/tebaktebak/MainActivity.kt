package com.nasza.tebaktebak

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nasza.tebaktebak.model.FlagsData

class MainActivity : AppCompatActivity() {

    private lateinit var flagImageView: ImageView
    private lateinit var scoreTextView: TextView
    private lateinit var answerBoxLayout: LinearLayout
    private lateinit var letterBoxLayout: LinearLayout
    private lateinit var submitButton: Button
    private lateinit var helpButton: Button

    private var score: Int = 0
    private var correctAnswer: String = ""
    private var currentAnswer: MutableList<Char> = mutableListOf()  // Jawaban sementara
    private val flags = FlagsData.getFlags().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flagImageView = findViewById(R.id.flagImageView)
        scoreTextView = findViewById(R.id.scoreTextView)
        answerBoxLayout = findViewById(R.id.answerBoxLayout)
        letterBoxLayout = findViewById(R.id.letterBoxLayout)
        submitButton = findViewById(R.id.submitButton)
        helpButton = findViewById(R.id.helpButton)

        loadNewFlag()

        submitButton.setOnClickListener {
            checkAnswer()
        }

        helpButton.setOnClickListener {
            showHelpDialog()
        }
    }

    private fun loadNewFlag() {
        if (flags.isNotEmpty()) {
            // Pilih negara secara acak
            val randomFlag = flags.removeAt((flags.indices).random())
            correctAnswer = randomFlag.country.uppercase()
            flagImageView.setImageResource(randomFlag.imageRes)

            // Reset jawaban pemain
            currentAnswer.clear()

            // Buat kotak jawaban kosong sebanyak huruf di nama negara
            setupAnswerBoxes()

            // Tampilkan huruf yang dapat dipilih
            setupLetterBoxes()
        } else {
            endGame()
        }
    }

    private fun setupAnswerBoxes() {
        // Hapus kotak sebelumnya
        answerBoxLayout.removeAllViews()

        // Tambahkan kotak kosong sesuai dengan jumlah huruf pada jawaban
        for (i in correctAnswer.indices) {
            val letterView = TextView(this).apply {
                text = "_"
                textSize = 24f
                setPadding(8, 8, 8, 8)
            }
            answerBoxLayout.addView(letterView)
        }
    }

    private fun setupLetterBoxes() {
        // Hapus kotak huruf sebelumnya
        letterBoxLayout.removeAllViews()

        // Buat daftar huruf acak termasuk huruf dari jawaban yang benar
        val letters = correctAnswer.toList().shuffled()

        // Tambahkan TextView untuk setiap huruf yang bisa dipilih
        for (letter in letters) {
            val letterView = TextView(this).apply {
                text = letter.toString()
                textSize = 24f
                setPadding(8, 8, 8, 8)
                setOnClickListener { onLetterClicked(letter) }
            }
            letterBoxLayout.addView(letterView)
        }
    }

    private fun onLetterClicked(letter: Char) {
        // Cek apakah masih ada kotak kosong untuk diisi
        val index = currentAnswer.size
        if (index < correctAnswer.length) {
            currentAnswer.add(letter)

            // Update tampilan kotak jawaban
            (answerBoxLayout.getChildAt(index) as TextView).text = letter.toString()
        }
    }

    private fun checkAnswer() {
        val userAnswer = currentAnswer.joinToString("")

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong! Correct answer is $correctAnswer", Toast.LENGTH_SHORT).show()
        }

        scoreTextView.text = "Score: $score"
        loadNewFlag()
    }

    private fun showHelpDialog() {
        // Tampilkan dialog bantuan
        AlertDialog.Builder(this)
            .setTitle("Help")
            .setMessage("Guess the country by filling the letter boxes!")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun endGame() {
        // Tampilkan dialog skor akhir
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage("Congratulations! Your final score is: $score")
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
