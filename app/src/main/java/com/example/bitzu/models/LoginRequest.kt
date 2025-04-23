package com.example.bitzu.models

data class LoginRequest(
    private var email: String,
    private var senha: String
) {
    val _email: String
        get() = email

    val _senha: String
        get() = senha
}
