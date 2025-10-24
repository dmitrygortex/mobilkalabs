package com.example.mobilkalaba1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class SignInFragment : Fragment() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignUp: TextView
    private lateinit var tvRegisteredUser: TextView

    private var registeredName: String? = null
    private var registeredEmail: String? = null
    private var registeredPassword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация views
        etEmail = view.findViewById(R.id.et_email)
        etPassword = view.findViewById(R.id.et_password)
        btnLogin = view.findViewById(R.id.btn_login)
        tvSignUp = view.findViewById(R.id.tv_sign_up)
        tvRegisteredUser = view.findViewById(R.id.tv_registered_user)

        // Получаем данные из Bundle (если пришли от SignUpFragment)
        arguments?.let { bundle ->
            registeredName = bundle.getString("registered_name")
            registeredEmail = bundle.getString("registered_email")
            registeredPassword = bundle.getString("registered_password")

            // Отображаем информацию о зарегистрированном пользователе
            if (registeredName != null && registeredEmail != null) {
                tvRegisteredUser.text = "Зарегистрирован: $registeredName\n$registeredEmail"
                tvRegisteredUser.visibility = View.VISIBLE

                Toast.makeText(
                    requireContext(),
                    "Пользователь зарегистрирован! Теперь войдите",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // Обработчик кнопки входа
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (validateLogin(email, password)) {
                (activity as? MainActivity)?.navigateToHome(registeredName ?: "Пользователь")
            }
        }

        // Обработчик перехода на регистрацию
        tvSignUp.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignUp(
                registeredName,
                registeredEmail,
                registeredPassword
            )
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        }

        if (registeredEmail != null && registeredPassword != null) {
            if (email == registeredEmail && password == registeredPassword) {
                Toast.makeText(requireContext(), "Вход выполнен успешно!", Toast.LENGTH_SHORT).show()
                return true
            } else {
                Toast.makeText(requireContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                return false
            }
        } else {
            if (email.contains("@") && password.length >= 6) {
                Toast.makeText(requireContext(), "Вход выполнен успешно!", Toast.LENGTH_SHORT).show()
                return true
            } else {
                Toast.makeText(
                    requireContext(),
                    "Введите корректный email и пароль (минимум 6 символов)",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
    }
}
