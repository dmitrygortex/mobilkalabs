package com.example.mobilkalaba1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pomodorotimer.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // на первом запуске будет онборд фрагмент
        if (savedInstanceState == null) {
            navigateToOnboard()
        }
    }

    // навигация между фрагментами

    fun navigateToOnboard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OnboardFragment())
            .commit()
    }

    fun navigateToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignInFragment())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToSignUp(name: String? = null, email: String? = null, password: String? = null) {
        val fragment = SignUpFragment()

        // передача данных через Bundle
        if (name != null || email != null || password != null) {
            val bundle = Bundle().apply {
                name?.let { putString("name", it) }
                email?.let { putString("email", it) }
                password?.let { putString("password", it) }
            }
            fragment.arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignUpFragment())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToHome(userName: String) {
        val fragment = HomeFragment()

        // передача имени пользователя через Bundle
        val bundle = Bundle().apply {
            putString("USER_NAME", userName)
        }
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // метод для возврата данных из SignUpFragment в SignInFragment
    fun returnToSignInWithData(name: String, email: String, password: String) {
        // создаем новый SignInFragment с данными
        val fragment = SignInFragment()
        val bundle = Bundle().apply {
            putString("registered_name", name)
            putString("registered_email", email)
            putString("registered_password", password)
        }
        fragment.arguments = bundle

        // возвращаемся назад по бэкстеку
        supportFragmentManager.popBackStack()

        // обновляем SignInFragment с новыми данными
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
