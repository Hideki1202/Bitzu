package com.example.bitzu.services
import com.example.bitzu.dtos.Token
import com.example.bitzu.models.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<Token>
}