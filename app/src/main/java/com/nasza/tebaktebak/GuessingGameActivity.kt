package com.nasza.tebaktebak

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GuessingGameActivity : AppCompatActivity() {

    private lateinit var flagImageView: ImageView
    private lateinit var submitButton: Button
    private lateinit var statusImageView: ImageView
    private lateinit var errorImageView: ImageView
    private lateinit var correctFlag: Flag
    private lateinit var answerBoxesLayout: GridLayout
    private lateinit var hiddenEditText: EditText

    private var currentIndex = 0 // Index for the currently focused answer box

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessing_game)

        flagImageView = findViewById(R.id.flagImageView)
        submitButton = findViewById(R.id.submitButton)
        statusImageView = findViewById(R.id.statusImageView)
        errorImageView = findViewById(R.id.errorImageView)
        answerBoxesLayout = findViewById(R.id.answerBoxesLayout)
        hiddenEditText = findViewById(R.id.hiddenEditText)
        hiddenEditText.visibility = View.GONE

        val flag = intent.getParcelableExtra<Flag>("FLAG")

        if (flag != null) {
            correctFlag = flag
            flagImageView.setImageResource(flag.imageResId)
            createAnswerBoxes(correctFlag.country.length)
            setupTextWatcher()
        } else {
            Toast.makeText(this, "No flag data found", Toast.LENGTH_SHORT).show()
            finish()
        }

        submitButton.setOnClickListener {
            val userAnswer = getUserAnswer()
            checkAnswer(userAnswer)
        }
    }

    private fun createAnswerBoxes(length: Int) {
        val boxSizeDp = 40 // Size of each box in dp
        val boxMarginDp = 4 // Margin between boxes in dp
        val boxMarginPx = (boxMarginDp * resources.displayMetrics.density).toInt()
        val boxSizePx = (boxSizeDp * resources.displayMetrics.density).toInt()

        val columns = 5 // Number of columns

        answerBoxesLayout.columnCount = columns
        answerBoxesLayout.rowCount = (length + columns - 1) / columns // Calculate number of rows

        for (i in 0 until length) {
            val textView = TextView(this).apply {
                setTextAppearance(R.style.AnswerBox)
                text = ""
                textSize = 18f
                setPadding(0, 0, 0, 0)

                val params = GridLayout.LayoutParams().apply {
                    width = boxSizePx
                    height = boxSizePx
                    setMargins(boxMarginPx, boxMarginPx, boxMarginPx, boxMarginPx)
                    rowSpec = GridLayout.spec(i / columns)
                    columnSpec = GridLayout.spec(i % columns)
                }
                layoutParams = params

                background = getDrawable(R.drawable.box_background)
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                gravity = android.view.Gravity.CENTER
                id = View.generateViewId()

                setOnClickListener { handleBoxClick(this) }
            }
            answerBoxesLayout.addView(textView)
        }
    }


    private fun setupTextWatcher() {
        hiddenEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("GuessingGameActivity", "beforeTextChanged: s=$s, start=$start, count=$count, after=$after")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("GuessingGameActivity", "onTextChanged: input='$s', start=$start, before=$before, count=$count")

                if (s != null) {
                    val input = s.toString()

                    if (count > before) {
                        // Character added
                        if (input.isNotEmpty()) {
                            val char = input[0]
                            if (char.isLetter()) {
                                if (currentIndex in 0 until answerBoxesLayout.childCount) {
                                    updateAnswerBox(currentIndex, char)
                                    currentIndex++
                                    hiddenEditText.setText("")
                                    if (currentIndex >= answerBoxesLayout.childCount) {
                                        currentIndex = answerBoxesLayout.childCount - 1
                                    }
                                    if (currentIndex >= 0) {
                                        val targetView = answerBoxesLayout.getChildAt(currentIndex)
                                        if (targetView is TextView) {
                                            showEditTextAt(targetView)
                                        }
                                    }
                                }
                            }
                        }
                    } else if (before > count) {
                        // Character deleted
                        if (currentIndex >= 0) {
                            // Reset character in the correct answer box
                            updateAnswerBox(currentIndex, ' ')
                            hiddenEditText.setText("")
                            if (currentIndex in 0 until answerBoxesLayout.childCount) {
                                val targetView = answerBoxesLayout.getChildAt(currentIndex)
                                if (targetView is TextView) {
                                    showEditTextAt(targetView)
                                }
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })
    }

    private fun handleBoxClick(textView: TextView) {
        val index = answerBoxesLayout.indexOfChild(textView)
        if (index != -1) {
            // Clear the content of the selected box
            textView.text = ""
            currentIndex = index
            showEditTextAt(textView)
        }
    }

    private fun showEditTextAt(targetView: TextView) {
        val gridParams = targetView.layoutParams as? GridLayout.LayoutParams
        if (gridParams != null) {
            hiddenEditText.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                (this as RelativeLayout.LayoutParams).topMargin = targetView.top
                (this as RelativeLayout.LayoutParams).leftMargin = targetView.left
            }

            hiddenEditText.x = targetView.x
            hiddenEditText.y = targetView.y
            hiddenEditText.visibility = View.VISIBLE
            hiddenEditText.requestFocus()

            // Show keyboard
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(hiddenEditText, InputMethodManager.SHOW_IMPLICIT)
        } else {
            // Notify if targetView is not a GridLayout item
            Toast.makeText(this, "Error: Target view is not a GridLayout item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateAnswerBox(index: Int, char: Char) {
        if (index in 0 until answerBoxesLayout.childCount) {
            val textView = answerBoxesLayout.getChildAt(index) as? TextView
            if (textView != null) {
                // Only update if the new character is different from the existing one
                if (textView.text.toString() != char.toString()) {
                    textView.text = char.toString()
                    Log.d("GuessingGameActivity", "Updating box at index $index with char $char")
                }
            } else {
                Log.e("GuessingGameActivity", "Error: Child at index $index is not a TextView")
            }
        }
    }

    private fun getUserAnswer(): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until answerBoxesLayout.childCount) {
            val textView = answerBoxesLayout.getChildAt(i) as? TextView
            if (textView != null) {
                val text = textView.text.toString()
                Log.d("GuessingGameActivity", "Box $i has text: $text")
                stringBuilder.append(text)
            } else {
                Log.e("GuessingGameActivity", "Error: Child at index $i is not a TextView")
            }
        }
        val result = stringBuilder.toString().trim()
        Log.d("GuessingGameActivity", "User answer: $result")
        return result
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
