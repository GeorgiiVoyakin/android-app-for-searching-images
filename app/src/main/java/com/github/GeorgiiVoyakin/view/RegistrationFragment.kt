package com.github.GeorgiiVoyakin.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.GeorgiiVoyakin.databinding.FragmentRegistrationBinding
import com.github.GeorgiiVoyakin.network.FastAPI
import com.github.GeorgiiVoyakin.network.Token
import com.github.GeorgiiVoyakin.network.User
import com.github.GeorgiiVoyakin.network.UserCreate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "RegistrationFragment"
private const val BASE_URL = "http://10.0.2.2:8000"

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var retrofit: Retrofit
    private lateinit var api: FastAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        sharedPreferences =
            requireContext().getSharedPreferences("pivg_prefs", Context.MODE_PRIVATE)
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(FastAPI::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            if (binding.login.text.isNotBlank() &&
                binding.password.text.toString() == binding.passwordRepeat.text.toString()
            ) {
                navigateToMainScreenIfRegistrationSuccessful()
            }
        }
    }

    private fun navigateToMainScreenIfRegistrationSuccessful() {
        val call =
            api.createUser(
                UserCreate(
                    binding.login.text.toString().trim(),
                    binding.login.text.toString().trim(),
                    binding.password.text.toString()
                )
            )

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d(TAG, "onResponse: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: response is successful ${response.body()}")
                    getAccessToken()
                    val action =
                        RegistrationFragmentDirections
                            .actionRegistrationFragmentToMainFragment()
                    findNavController().navigate(action)
                } else {
                    Log.d(TAG, "onResponse: response is not successful")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d(TAG, "onFailure: not ok, ${t.message}")
            }
        })
    }

    // TODO: вынести в отдельный файл
    private fun getAccessToken() {
        val call = api.loginForAccessToken(
            binding.login.text.toString(),
            binding.password.text.toString()
        )

        call.enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                Log.d(TAG, "onResponse: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: response is successful ${response.body()}")

                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("access_token", response.body()!!.accessToken)
                    editor.apply()
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