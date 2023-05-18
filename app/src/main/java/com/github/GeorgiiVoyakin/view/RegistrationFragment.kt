package com.github.GeorgiiVoyakin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.GeorgiiVoyakin.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            // Переходим на главный экран
            val action =
                RegistrationFragmentDirections
                    .actionRegistrationFragmentToMainFragment()
            it.findNavController().navigate(action)
        }
    }
}