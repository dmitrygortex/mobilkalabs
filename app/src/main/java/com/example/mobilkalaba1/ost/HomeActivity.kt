package com.example.mobilkalaba1

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class HomeActivity : BaseActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button
    private lateinit var tvSessions: TextView

    private var timer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 1500000 // 25 минут
    private var isRunning = false
    private var sessionsCompleted = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvTimer = findViewById(R.id.tv_timer)
        tvStatus = findViewById(R.id.tv_status)
        btnStart = findViewById(R.id.btn_start)
        btnPause = findViewById(R.id.btn_pause)
        btnReset = findViewById(R.id.btn_reset)
        tvSessions = findViewById(R.id.tv_sessions)

        updateTimerText()
        updateSessionsText()

        btnStart.setOnClickListener {
            startTimer()
        }

        btnPause.setOnClickListener {
            pauseTimer()
        }

        btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                isRunning = false
                sessionsCompleted++
                updateSessionsText()
                tvStatus.text = "Сессия завершена! 🎉"
                timeLeftInMillis = 1500000
                updateTimerText()
            }
        }.start()

        isRunning = true
        tvStatus.text = "Работаем! 💪"
    }

    private fun pauseTimer() {
        timer?.cancel()
        isRunning = false
        tvStatus.text = "На паузе ⏸️"
    }

    private fun resetTimer() {
        timer?.cancel()
        timeLeftInMillis = 1500000
        updateTimerText()
        isRunning = false
        tvStatus.text = "Готов к работе! 🍅"
    }

    private fun updateTimerText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateSessionsText() {
        tvSessions.text = "Сессий завершено: $sessionsCompleted"
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
