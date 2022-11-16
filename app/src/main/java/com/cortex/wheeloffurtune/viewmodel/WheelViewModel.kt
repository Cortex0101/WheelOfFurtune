package com.cortex.wheeloffurtune.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WheelViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WheelUiState())
    val uiState: StateFlow<WheelUiState> = _uiState.asStateFlow()

    init {
        resetState()
    }

    fun spin() {
        val result = (0..15).random() // Which of the 16 options?
        val extraMovement = (1..21).random() // Where on the chosen option should the arrow point
        val wheelRotation = (result * 22.5f) + extraMovement

        _uiState.update { currentState ->
            currentState.copy(wheelRotationDegree = wheelRotation,
                              wheelResult = getWheelResult(wheelRotation))
        }
    }

    fun resetState() {
        _uiState.value = WheelUiState()
    }

    fun getWheelResultAsInt(result: WheelResult): Int {
        return when (result) {
            WheelResult.FIVE_HUNDRED -> 500
            WheelResult.SEVEN_HUNDRED_FIFTY -> 750
            WheelResult.ONE_THOUSAND -> 1000
            WheelResult.ONE_THOUSAND_FIVE_HUNDRED -> 1500
            WheelResult.TWO_THOUSAND -> 2000
            WheelResult.TWO_THOUSAND_FIVE_HUNDRED -> 2500
            WheelResult.THREE_THOUSAND -> 3000
            WheelResult.FOUR_THOUSAND -> 4000
            WheelResult.SEVENTEEN_THOUSAND_FIVE_HUNDRED -> 17500
            WheelResult.TWENTY_FIVE_THOUSAND -> 25000
            WheelResult.BANKRUPT -> -1
            WheelResult.NONE -> 0
        }
    }

    private fun getWheelResult(degree: Float): WheelResult {
        if (degree >= 0f && degree < 22.5f) {
            return WheelResult.BANKRUPT
        } else if (degree >= 22.5f && degree < 45f) {
            return WheelResult.ONE_THOUSAND_FIVE_HUNDRED
        }  else if (degree >= 45f && degree < 67.5f) {
            return WheelResult.TWO_THOUSAND_FIVE_HUNDRED
        }  else if (degree >= 67.5f && degree < 90f) {
            return WheelResult.THREE_THOUSAND
        }  else if (degree >= 90f && degree < 112.5f) {
            return WheelResult.FOUR_THOUSAND
        }  else if (degree >= 112.5f && degree < 135f) {
            return WheelResult.SEVEN_HUNDRED_FIFTY
        } else if (degree >= 135 && degree < 157.5f) {
            return WheelResult.FIVE_HUNDRED
        } else if (degree >= 157.5f && degree < 180f) {
            return WheelResult.BANKRUPT
        } else if (degree >= 180f && degree < 202.5f) {
            return WheelResult.SEVENTEEN_THOUSAND_FIVE_HUNDRED
        } else if (degree >= 202.5f && degree < 225f) {
            return WheelResult.ONE_THOUSAND_FIVE_HUNDRED
        } else if (degree >= 225f && degree < 247.5f) {
            return WheelResult.SEVEN_HUNDRED_FIFTY
        } else if (degree >= 247.5f && degree < 270f) {
            return WheelResult.FIVE_HUNDRED
        } else if (degree >= 270f && degree < 292.5f) {
            return WheelResult.ONE_THOUSAND
        } else if (degree >= 292.5f && degree < 315f) {
            return WheelResult.TWO_THOUSAND
        } else if (degree >= 315f && degree < 337.5f) {
            return WheelResult.TWO_THOUSAND_FIVE_HUNDRED
        } else if (degree >= 337.5f && degree < 360f) {
            return WheelResult.TWENTY_FIVE_THOUSAND
        } else {
            return WheelResult.NONE
        }
    }
}