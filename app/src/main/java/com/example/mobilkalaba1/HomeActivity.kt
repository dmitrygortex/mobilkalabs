package com.example.mobilkalaba1

import android.os.Bundle
import android.widget.TextView

class HomeActivity : LoggedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userName = intent.getStringExtra("USER_NAME") ?: "Пользователь"

        val tvStatus = findViewById<TextView>(R.id.tv_status)
        tvStatus.text = "Добро пожаловать, $userName! 🍅"
    }
}
