package com.nasza.tebaktebak

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var settingsButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        startButton = findViewById(R.id.startButton)
        settingsButton = findViewById(R.id.settingsButton)
        exitButton = findViewById(R.id.exitButton)

        startButton.setOnClickListener {
            // Mulai permainan
            val intent = Intent(this, LevelMenuActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            // Buka pengaturan (bisa tambahkan activity Settings)
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            // Keluar dari aplikasi
            finishAffinity()
        }
    }
}

class SettingsActivity {

}
