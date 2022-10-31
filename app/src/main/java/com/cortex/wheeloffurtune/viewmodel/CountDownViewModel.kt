package com.cortex.wheeloffurtune.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cortex.wheeloffurtune.utils.Utility.formatTime
import com.cortex.wheeloffurtune.helper.SingleLiveEvent
import com.cortex.wheeloffurtune.utils.Utility

class CountDownViewModel : ViewModel() {
    private val _time = MutableLiveData(Utility.TIME_COUNTDOWN.formatTime())
    val time: LiveData<String> = _time
    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float> = _progress
    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying
    private val _celebrate = SingleLiveEvent<Boolean>()
    val celebrate : LiveData<Boolean> get() =  _celebrate

    private val countDownTimer = object : CountDownTimer(Utility.TIME_COUNTDOWN, 1000) {

        override fun onTick(millisRemaining: Long) {
            val progressValue = millisRemaining.toFloat() / Utility.TIME_COUNTDOWN
            setTimerValues(true, millisRemaining.formatTime(), progressValue, false)
            _celebrate.postValue(false)
        }

        override fun onFinish() {
            pauseTimer()
            _celebrate.postValue(true)
        }
    }.start()

    fun handleCountDownTimer() {
        if (isPlaying.value == true) {
            pauseTimer()
            _celebrate.postValue(false)
        } else {
            startTimer()
        }
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        setTimerValues(false, Utility.TIME_COUNTDOWN.formatTime(), 1.0F, false)

    }

    private fun startTimer() {

        _isPlaying.value = true
        countDownTimer.start()
    }

    private fun setTimerValues(isPlaying: Boolean, text: String, progress: Float, celebrate: Boolean) {
        _isPlaying.value = isPlaying
        _time.value = text
        _progress.value = progress
        _celebrate.postValue(celebrate)
    }
}