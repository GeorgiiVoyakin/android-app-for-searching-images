package com.github.GeorgiiVoyakin.network

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("hashed_password")
    val hashedPassword: String
)
