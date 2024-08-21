package com.nasza.tebaktebak

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flag(
    val imageResId: Int,
    val country: String,
    var isAnswered: Boolean = false,
    var isCorrect: String = false.toString(),
    var status: FlagStatus = FlagStatus.UNANSWERED
) : Parcelable



