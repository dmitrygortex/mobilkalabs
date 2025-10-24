//package com.example.mobilkalaba1.ost
//
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import com.example.mobilkalaba1.R
//
//class SignInActivity : BaseActivity() {
//
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var btnLogin: Button
//    private lateinit var tvSignUp: TextView
//    private lateinit var tvRegisteredUser: TextView
//
//    // Лаунчер для получения результата из SignUpActivity
//    private val signUpLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == RESULT_OK) {
//            val data = result.data
//
//            // Вариант 1: Получение данных через стандартные типы (String)
//            val userName = data?.getStringExtra("USER_NAME")
//            val userEmail = data?.getStringExtra("USER_EMAIL")
//
//            // Вариант 2: Получение объекта User (Parcelable) - правильный способ
//            val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                data?.getParcelableExtra("USER_OBJECT", User::class.java)
//            } else {
//                @Suppress("DEPRECATION")
//                data?.getParcelableExtra("USER_OBJECT")
//            }
//
//            // Показываем полученные данные
//            if (user != null) {
//                tvRegisteredUser.text = "Зарегистрирован: ${user.name}\nEmail: ${user.email}"
//                tvRegisteredUser.visibility = TextView.VISIBLE
//
//                // Автозаполнение полей
//                etEmail.setText(user.email)
//                etPassword.setText(user.password)
//
//                Toast.makeText(this, "Пользователь ${user.name} зарегистрирован!", Toast.LENGTH_SHORT).show()
//            } else if (userName != null && userEmail != null) {
//                tvRegisteredUser.text = "Зарегистрирован: $userName\nEmail: $userEmail"
//                tvRegisteredUser.visibility = TextView.VISIBLE
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)
//
//        etEmail = findViewById(R.id.et_email)
//        etPassword = findViewById(R.id.et_password)
//        btnLogin = findViewById(R.id.btn_login)
//        tvSignUp = findViewById(R.id.tv_sign_up)
//        tvRegisteredUser = findViewById(R.id.tv_registered_user)
//
//        btnLogin.setOnClickListener {
//            val email = etEmail.text.toString()
//            val password = etPassword.text.toString()
//
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
//            } else {
//                // Проверка введенных данных
//                Toast.makeText(this, "Вход выполнен!", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//            }
//        }
//
//        tvSignUp.setOnClickListener {
//            val intent = Intent(this, SignUpActivity::class.java)
//            signUpLauncher.launch(intent)
//        }
//    }
//}
