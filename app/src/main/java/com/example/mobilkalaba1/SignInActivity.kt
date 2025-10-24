package com.example.mobilkalaba1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : LoggedActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignUp: TextView
    private lateinit var tvRegisteredUser: TextView

    private var registeredName: String? = null
    private var registeredEmail: String? = null
    private var registeredPassword: String? = null

    private val signUpLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data

            registeredName = data?.getStringExtra(SignUpResultKeys.KEY_NAME)
            registeredEmail = data?.getStringExtra(SignUpResultKeys.KEY_EMAIL)
            registeredPassword = data?.getStringExtra(SignUpResultKeys.KEY_PASSWORD)

            val user = data?.getSerializableExtra(SignUpResultKeys.KEY_USER) as? User

            val userParcelable = data?.getParcelableExtra<UserParcelable>(
                SignUpResultKeys.KEY_USER_PARCELABLE
            )

            if (registeredName != null && registeredEmail != null) {
                tvRegisteredUser.text = "Зарегистрирован: $registeredName\n$registeredEmail"
                tvRegisteredUser.visibility = android.view.View.VISIBLE

                Toast.makeText(
                    this,
                    "Пользователь зарегистрирован! Теперь войдите",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvSignUp = findViewById(R.id.tv_sign_up)
        tvRegisteredUser = findViewById(R.id.tv_registered_user)

        // Получаем данные, если пришли из SignUpActivity без результата (просто "Назад")
        intent.getStringExtra(SignUpResultKeys.KEY_NAME)?.let { name ->
            registeredName = name
            registeredEmail = intent.getStringExtra(SignUpResultKeys.KEY_EMAIL)
            registeredPassword = intent.getStringExtra(SignUpResultKeys.KEY_PASSWORD)

            if (registeredName != null && registeredEmail != null) {
                tvRegisteredUser.text = "Зарегистрирован: $registeredName\n$registeredEmail"
                tvRegisteredUser.visibility = android.view.View.VISIBLE
            }
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (validateLogin(email, password)) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_NAME", registeredName ?: "Пользователь")
                startActivity(intent)
                finish()
            }
        }

        tvSignUp.setOnClickListener {
            // Передаем текущие данные в SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra(SignUpResultKeys.KEY_NAME, registeredName)
            intent.putExtra(SignUpResultKeys.KEY_EMAIL, registeredEmail)
            intent.putExtra(SignUpResultKeys.KEY_PASSWORD, registeredPassword)
            signUpLauncher.launch(intent)
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        }

        if (registeredEmail != null && registeredPassword != null) {
            if (email == registeredEmail && password == registeredPassword) {
                Toast.makeText(this, "Вход выполнен успешно!", Toast.LENGTH_SHORT).show()
                return true
            } else {
                Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                return false
            }
        } else {
            if (email.contains("@") && password.length >= 6) {
                Toast.makeText(this, "Вход выполнен успешно!", Toast.LENGTH_SHORT).show()
                return true
            } else {
                Toast.makeText(
                    this,
                    "Введите корректный email и пароль (минимум 6 символов)",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
    }
}

