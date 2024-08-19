package com.nasza.tebaktebak.model

import com.nasza.tebaktebak.R

data class Flag(val country: String, val imageRes: Int)

object FlagsData {
    fun getFlags(): List<Flag> {
        return listOf(
            Flag("Indonesia", R.drawable.indonesia),
            Flag("United States", R.drawable.usa),
            Flag("Japan", R.drawable.japan),
            Flag("Germany", R.drawable.germany),
            Flag("Brazil", R.drawable.brazil)
            // Tambahkan negara lain di sini
        )
    }
}