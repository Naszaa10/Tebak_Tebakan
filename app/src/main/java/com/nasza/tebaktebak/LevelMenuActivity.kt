package com.nasza.tebaktebak

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class LevelMenuActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_menu)

        // Setup buttons
        findViewById<Button>(R.id.level1Button).setOnClickListener {
            openLevel(1)
        }
        findViewById<Button>(R.id.level2Button).setOnClickListener {
            openLevel(2)
        }
        findViewById<Button>(R.id.level3Button).setOnClickListener {
            openLevel(3)
        }
        findViewById<Button>(R.id.level4Button).setOnClickListener {
            openLevel(4)
        }
        findViewById<Button>(R.id.level5Button).setOnClickListener {
            openLevel(5)
        }
    }

    private fun openLevel(level: Int) {
        val intent = Intent(this, GuessingGameActivity::class.java)
        intent.putExtra("LEVEL", level)  // Pass level info to next activity
        startActivity(intent)
    }
}
