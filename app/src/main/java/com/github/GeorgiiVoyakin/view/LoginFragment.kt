package com.github.GeorgiiVoyakin.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.github.GeorgiiVoyakin.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isLoggedIn()) {
            navigateToMainScreen()
        }

        binding.loginButton.setOnClickListener {
            // Переходим на главный экран
            val action =
                LoginFragmentDirections
                    .actionLoginFragmentToGalleryFragment()
            it.findNavController().navigate(action)
        }

        binding.registerButton.setOnClickListener {
            // Переходим на экран регистрации
            val action =
                LoginFragmentDirections
                    .actionLoginFragmentToRegistrationFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun navigateToMainScreen() {
        val action =
            LoginFragmentDirections
                .actionLoginFragmentToGalleryFragment()
        findNavController().navigate(action)
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences =
            context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean("is_logged_in", false) ?: false
    }

}
