package com.example.pomodorotimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobilkalaba1.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // получаем имя пользователя из Bundle
        val userName = arguments?.getString("USER_NAME") ?: "Пользователь"

        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        tvStatus.text = "Добро пожаловать, $userName! 🍅"
    }
}
