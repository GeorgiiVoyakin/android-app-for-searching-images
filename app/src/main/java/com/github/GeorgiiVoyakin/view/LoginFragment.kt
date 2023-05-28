package com.github.GeorgiiVoyakin.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.github.GeorgiiVoyakin.databinding.FragmentLoginBinding
import com.github.GeorgiiVoyakin.network.FastAPI
import com.github.GeorgiiVoyakin.network.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "LoginFragment"
private const val BASE_URL = "http://10.0.2.2:8000"

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
            performLogin()
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

    private fun performLogin() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: FastAPI = retrofit.create(FastAPI::class.java)
        val call = api.loginForAccessToken(
            binding.login.text.toString(),
            binding.password.text.toString()
        )

        call.enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                Log.d(TAG, "onResponse: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: response is successful ${response.body()}")
                    val action =
                        LoginFragmentDirections
                            .actionLoginFragmentToGalleryFragment()
                    findNavController().navigate(action)
                } else {
                    Log.d(TAG, "onResponse: response is not successful")
                    Toast.makeText(
                        activity,
                        "Не верное имя пользователя или пароль",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Не удалось выполнить вход, проверьте интернет соединение",
                    Toast.LENGTH_LONG
                )
                    .show()
                Log.d(TAG, "onFailure: not ok, ${t.message}")
            }
        })
    }
}
