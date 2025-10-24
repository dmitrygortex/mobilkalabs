package com.example.mobilkalaba1.ost

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mobilkalaba1.R

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val etName = findViewById<EditText>(R.id.et_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etAge = findViewById<EditText>(R.id.et_age)
        val spinnerGender = findViewById<Spinner>(R.id.spinner_gender)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        val genders = arrayOf("Выберите пол", "Мужской", "Женский")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val age = etAge.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                // Создаем Intent для возврата данных
                val resultIntent = Intent()

                // Вариант 1: Передача через стандартные типы (String)
                resultIntent.putExtra("USER_NAME", name)
                resultIntent.putExtra("USER_EMAIL", email)
                resultIntent.putExtra("USER_PASSWORD", password)

                // Вариант 2: Передача объекта User (Parcelable)
                val user = User(name, email, password)
                resultIntent.putExtra("USER_OBJECT", user)

                setResult(RESULT_OK, resultIntent)
                Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
