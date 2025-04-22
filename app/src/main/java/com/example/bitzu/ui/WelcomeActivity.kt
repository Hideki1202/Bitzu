package com.example.bitzu.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitzu.R

class WelcomeActivity : AppCompatActivity() {
    lateinit var loginButton: AppCompatButton;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)
        loginButton = findViewById(R.id.loginButtonWelcome)
        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}