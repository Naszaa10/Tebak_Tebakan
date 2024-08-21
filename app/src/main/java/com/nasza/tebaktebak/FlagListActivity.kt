package com.nasza.tebaktebak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FlagListActivity : AppCompatActivity() {

    private lateinit var flagRecyclerView: RecyclerView
    private lateinit var flagAdapter: FlagAdapter
    private lateinit var flagList: MutableList<Flag> // List of Flag items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flag_list)

        flagRecyclerView = findViewById(R.id.recyclerView)
        flagRecyclerView.layoutManager = GridLayoutManager(this, 3) // 3 columns

        // Initialize flag list and adapter
        flagList = loadFlags() // This should be replaced with actual data fetching
        flagAdapter = FlagAdapter(flagList) { flag ->
            openGuessingGame(flag)
        }
        flagRecyclerView.adapter = flagAdapter
    }

    private fun loadFlags(): MutableList<Flag> {
        // Replace with actual loading logic, possibly from a database or API
        return mutableListOf(
            // Example flags, replace with real data
            Flag(R.drawable.indonesia, FlagJawaban.INDONESIA.toString()),
            Flag(R.drawable.brazil, FlagJawaban.Brazil.toString()),
            Flag(R.drawable.japan, FlagJawaban.Japan.toString()),
            // Add more flags as needed
        )
    }

    private fun openGuessingGame(flag: Flag) {
        val intent = Intent(this, GuessingGameActivity::class.java)
        intent.putExtra("FLAG", flag)
        startActivity(intent)
    }
}
