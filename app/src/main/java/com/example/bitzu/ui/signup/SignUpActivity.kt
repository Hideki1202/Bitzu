package com.example.bitzu.ui.signup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.bitzu.R
import com.example.bitzu.models.User
import com.example.bitzu.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    lateinit var loginText: TextView;
    lateinit var emailInput: EditText;
    lateinit var passwordInput: EditText;
    lateinit var usernameInput: EditText;
    lateinit var birthDateInput: EditText;
    lateinit var phoneInput: EditText;
    lateinit var signUpButton: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        initializeComponents()
        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        signUpButton.setOnClickListener {
            println(getUserInForm())
            registerUser()
        }

    }
    fun registerUser(){
        RetrofitClient.createService(UserService::class.java).createUser(getUserInForm()).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val createdUser = response.body()
                    println("Usuário criado: $createdUser")
                    goToLoginScreen()
                } else {
                    println("Erro: ${response.code()}")
                   errorBuilder("Tente Novamente mais tarde")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                errorBuilder("Tente Novamente mais tarde")

            }
        })
    }
    fun errorBuilder(errorMessage: String){
        AlertDialog.Builder(this@SignUpActivity)
            .setTitle("Erro")
            .setMessage(errorMessage)
            .setNegativeButton("Fechar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    fun goToLoginScreen(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
    fun initializeComponents(){
        signUpButton = findViewById(R.id.SignUpButton)
        loginText = findViewById(R.id.logintextSignUp)
        emailInput = findViewById(R.id.emailSignUpInput)
        passwordInput = findViewById(R.id.passwordSignUpInput)
        phoneInput = findViewById(R.id.phoneSignUpInput)
        usernameInput = findViewById(R.id.nameSignUpInput)
        birthDateInput = findViewById(R.id.birthdaySignUpInput)

    }
    fun getUserInForm(): User {
        val username = usernameInput.text.toString()
        val phone = phoneInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val birthDate = birthDateInput.text.toString()


        return User(
            username = username,
            telefone = phone,
            email = email,
            senha = password,
            dataNascimento = birthDate,
            id = null
        )
    }


}