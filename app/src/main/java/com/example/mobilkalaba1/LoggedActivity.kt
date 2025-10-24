package com.example.mobilkalaba1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class LoggedActivity : AppCompatActivity() {

    private fun log(message: String) {
        val tag = this::class.simpleName ?: "Activity"
        Log.d(tag, message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        log("onPause")
        super.onPause()
    }

    override fun onStop() {
        log("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }
}