package com.nasza.tebaktebak

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LevelMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_menu)

        findViewById<Button>(R.id.level1Button).setOnClickListener {
            openLevel(1)
        }
    }

    private fun openLevel(level: Int) {
        val intent = Intent(this, FlagListActivity::class.java)
        intent.putExtra("LEVEL", level)
        startActivity(intent)
    }
}
