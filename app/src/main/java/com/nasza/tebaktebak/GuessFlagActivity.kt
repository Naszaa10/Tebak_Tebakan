import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nasza.tebaktebak.Flag
import com.nasza.tebaktebak.FlagStatus
import com.nasza.tebaktebak.R

class GuessingGameActivity : AppCompatActivity() {

    private lateinit var flagImageView: ImageView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var flag: Flag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessing_game)

        flagImageView = findViewById(R.id.flagImageView)
        answerEditText = findViewById(R.id.answerBoxesLayout)
        submitButton = findViewById(R.id.submitButton)

        // Get the flag object from the intent
        flag = intent.getParcelableExtra("FLAG") ?: throw IllegalStateException("Flag not found")

        // Set flag image
        flagImageView.setImageResource(flag.imageResId)

        submitButton.setOnClickListener {
            checkAnswer(answerEditText.text.toString().trim())
        }
    }

    private fun checkAnswer(userAnswer: String) {
        // Replace with actual correct answer check
        val correctAnswer = "Some Country" // Get this from your flag data or logic

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            flag.status = FlagStatus.CORRECT
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            flag.status = FlagStatus.INCORRECT
            Toast.makeText(this, "Incorrect! The correct answer is $correctAnswer", Toast.LENGTH_SHORT).show()
        }

        // Notify flag list activity to update the UI
        finish()
    }
}
