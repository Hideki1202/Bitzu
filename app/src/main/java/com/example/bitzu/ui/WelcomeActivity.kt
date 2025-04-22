package com.example.bitzu.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.bitzu.R

class WelcomeActivity : AppCompatActivity() {
    lateinit var loginButton: AppCompatButton;
    lateinit var signUpButton: AppCompatButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        loginButton = findViewById(R.id.loginButtonWelcome)
        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
        signUpButton = findViewById(R.id.signUpButtonWelcome)
        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

        }


    }
}