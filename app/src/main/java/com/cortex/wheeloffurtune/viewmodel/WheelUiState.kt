package com.cortex.wheeloffurtune.viewmodel

import com.cortex.wheeloffurtune.R

enum class WheelResult {
    FIVE_HUNDRED,
    SEVEN_HUNDRED_FIFTY,
    ONE_THOUSAND,
    ONE_THOUSAND_FIVE_HUNDRED,
    TWO_THOUSAND,
    TWO_THOUSAND_FIVE_HUNDRED,
    THREE_THOUSAND,
    FOUR_THOUSAND,
    SEVENTEEN_THOUSAND_FIVE_HUNDRED,
    TWENTY_FIVE_THOUSAND,
    BANKRUPT,
    NONE
}

/*
    first 90 degrees:
    0-22.5: 25.000
    22.5-45: 2.500
    45-67.5: 2.000
    67.5-90: 1000


 */

data class WheelUiState (
    val wheelResult: WheelResult = WheelResult.NONE,
    val wheelRotationDegree: Float = 0f,
    val wheelId: Int = R.drawable.wof
        )