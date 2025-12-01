package com.example.mobilkalaba1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobilkalaba1.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genderOptions = arrayOf("Выберите пол", "Мужской", "Женский", "Ещё какой то?")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && email.contains("@") && password.length >= 6) {
                // возврат на вход и передача через автоген SafeArgs >>>
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(
                    registeredName = name,
                    registeredEmail = email,
                    registeredPassword = password
                )
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Заполните поля корректно", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvBackToSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
