package com.cortex.wheeloffurtune.utils

import java.util.concurrent.TimeUnit

object Utility {
    const val TIME_COUNTDOWN: Long = 60000L
    private const val TIME_FORMAT = "%02d:%02d"


    // Convert time to milliseconds
    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )

}
