package com.example.bitzu.ui

import SessionManager
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.bitzu.R
import com.example.bitzu.dtos.Token
import com.example.bitzu.models.LoginRequest
import com.example.bitzu.services.AuthService
import retrofit2.Call

class LoginActivity : AppCompatActivity() {
    lateinit var signUpText: TextView;
    lateinit var loginButton : AppCompatButton
    lateinit var emailInput : TextView;
    lateinit var passwordInput : TextView;
    lateinit var loginRequest: LoginRequest


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        initializeComponents()
        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        loginButton.setOnClickListener {
            loginRequest = LoginRequest(emailInput.text.toString(), passwordInput.text.toString())
            loginRequest()

        }

    }
    fun loginRequest(){
        println(loginRequest)
        RetrofitClient.createService(AuthService::class.java).login(loginRequest).enqueue(object : retrofit2.Callback<Token> {
            override fun onResponse(call: Call<Token>, response: retrofit2.Response<Token>) {
                if (response.isSuccessful) {
                    val tokenCreated = response.body()
                    val session = SessionManager(this@LoginActivity)
                    session.saveToken(tokenCreated?.token ?: "")
                    println("Token: $tokenCreated")
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                } else {
                    println("Erro: ${response.code()}")
                    if (response.code() == 403){
                        errorBuilder("Senha ou email incorretos")
                    }
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    fun initializeComponents(){
        signUpText = findViewById(R.id.signuptextlogin)
        loginButton = findViewById(R.id.loginButton)
        emailInput = findViewById(R.id.emailLoginInput)
        passwordInput = findViewById(R.id.passwordLoginInput)
    }
    fun errorBuilder(errorMessage: String){
        AlertDialog.Builder(this)
            .setTitle("Erro")
            .setMessage(errorMessage)
            .setNegativeButton("Fechar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}