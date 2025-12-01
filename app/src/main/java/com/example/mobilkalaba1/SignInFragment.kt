package com.example.mobilkalaba1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobilkalaba1.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    // автоген SafeArgs
    private val args: SignInFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val regName = args.registeredName
        val regEmail = args.registeredEmail
        val regPassword = args.registeredPassword

        if (regName != null && regEmail != null) {
            binding.tvRegisteredUser.text = "Зарегистрирован: $regName\n$regEmail"
            binding.tvRegisteredUser.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Теперь войдите", Toast.LENGTH_LONG).show()

            binding.etEmail.setText(regEmail)
            if (regPassword != null) binding.etPassword.setText(regPassword)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.length >= 6) {
                val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment(
                    userName = regName ?: "rndm user"
                )
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Ошибка входа", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
