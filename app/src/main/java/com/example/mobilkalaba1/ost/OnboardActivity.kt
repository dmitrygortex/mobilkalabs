package com.example.mobilkalaba1

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class OnboardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        val btnStart = findViewById<Button>(R.id.btn_start)
        btnStart.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
