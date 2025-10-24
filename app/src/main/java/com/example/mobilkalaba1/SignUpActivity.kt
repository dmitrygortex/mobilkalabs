package com.example.mobilkalaba1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : LoggedActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var spinnerGender: Spinner
    private lateinit var btnRegister: Button
    private lateinit var tvBackToSignIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etAge = findViewById(R.id.et_age)
        spinnerGender = findViewById(R.id.spinner_gender)
        btnRegister = findViewById(R.id.btn_register)
        tvBackToSignIn = findViewById(R.id.tv_back_to_sign_in)

        val genderOptions = arrayOf("Выберите пол", "Мужской", "Женский", "Другой")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        // забираем все данные из SignInActivity, если они есть
        val existingName = intent.getStringExtra(SignUpResultKeys.KEY_NAME)
        val existingEmail = intent.getStringExtra(SignUpResultKeys.KEY_EMAIL)

        // сразу же заполняем поля, если данные уже были
        existingName?.let { etName.setText(it) }
        existingEmail?.let { etEmail.setText(it) }

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val age = etAge.text.toString()
            val gender = spinnerGender.selectedItem.toString()

            if (validateRegistration(name, email, password, age, gender)) {
                val resultIntent = Intent()

                resultIntent.putExtra(SignUpResultKeys.KEY_NAME, name)
                resultIntent.putExtra(SignUpResultKeys.KEY_EMAIL, email)
                resultIntent.putExtra(SignUpResultKeys.KEY_PASSWORD, password)

                val user = User(name, email, password)
                resultIntent.putExtra(SignUpResultKeys.KEY_USER, user)

                val userParcelable = UserParcelable(name, email, password)
                resultIntent.putExtra(SignUpResultKeys.KEY_USER_PARCELABLE, userParcelable)

                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        // обработчик (возвращает на экран входа)
        tvBackToSignIn.setOnClickListener {
            // закрываем текущую Activity, и идем обратно в в SignInActivity
            finish()
        }
    }

//    override fun onBackPressed()
//        finish()
//    }

    private fun validateRegistration(
        name: String,
        email: String,
        password: String,
        age: String,
        gender: String
    ): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(this, "Введите корректный email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            Toast.makeText(this, "Пароль должен содержать минимум 6 символов", Toast.LENGTH_SHORT).show()
            return false
        }

        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1) {
            Toast.makeText(this, "Введите корректный возраст", Toast.LENGTH_SHORT).show()
            return false
        }

        if (gender == "Выберите пол") {
            Toast.makeText(this, "Выберите пол", Toast.LENGTH_SHORT).show()
            return false
        }

        Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
        return true
    }
}
