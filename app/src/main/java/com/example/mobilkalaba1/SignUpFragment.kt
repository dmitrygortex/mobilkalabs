package com.example.mobilkalaba1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment : Fragment() {

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var spinnerGender: Spinner
    private lateinit var btnRegister: Button
    private lateinit var tvBackToSignIn: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработка кнопки "Назад"
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }
            }
        )

        etName = view.findViewById(R.id.et_name)
        etEmail = view.findViewById(R.id.et_email)
        etPassword = view.findViewById(R.id.et_password)
        etAge = view.findViewById(R.id.et_age)
        spinnerGender = view.findViewById(R.id.spinner_gender)
        btnRegister = view.findViewById(R.id.btn_register)
        tvBackToSignIn = view.findViewById(R.id.tv_back_to_sign_in)

        // Настройка Spinner
        val genderOptions = arrayOf("Выберите пол", "Мужской", "Женский", "Другой")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        // Получаем данные из Bundle (если есть)
        arguments?.let { bundle ->
            bundle.getString("name")?.let { etName.setText(it) }
            bundle.getString("email")?.let { etEmail.setText(it) }
        }

        // Обработчик кнопки регистрации
        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val age = etAge.text.toString()
            val gender = spinnerGender.selectedItem.toString()

            if (validateRegistration(name, email, password, age, gender)) {
                // Возвращаемся в SignInFragment с данными через MainActivity
                (activity as? MainActivity)?.returnToSignInWithData(name, email, password)
            }
        }

        // Обработчик возврата на вход
        tvBackToSignIn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun validateRegistration(
        name: String,
        email: String,
        password: String,
        age: String,
        gender: String
    ): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Введите имя", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(requireContext(), "Введите корректный email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            Toast.makeText(requireContext(), "Пароль должен содержать минимум 6 символов", Toast.LENGTH_SHORT).show()
            return false
        }

        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1) {
            Toast.makeText(requireContext(), "Введите корректный возраст", Toast.LENGTH_SHORT).show()
            return false
        }

        if (gender == "Выберите пол") {
            Toast.makeText(requireContext(), "Выберите пол", Toast.LENGTH_SHORT).show()
            return false
        }

        Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show()
        return true
    }
}
