package com.example.bitzu.ui.home

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bitzu.R
import com.example.bitzu.dtos.Token
import com.example.bitzu.models.User
import com.example.bitzu.services.AuthService
import com.example.bitzu.ui.indevelopment.InDevelopmentFragment
import com.example.bitzu.ui.login.LoginActivity
import com.example.bitzu.ui.projects.ProjectsFragment
import com.example.bitzu.ui.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    lateinit var profileIcon: ImageView;
    lateinit var overlay: View;
    lateinit var bottom_navigation: BottomNavigationView;
    lateinit var user: User;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        profileIcon = findViewById(R.id.profile_icon)
        bottom_navigation = findViewById(R.id.bottom_navigation)
        val sideSheet = findViewById<View>(R.id.modal_side_sheet)
        val behavior = SideSheetBehavior.from(sideSheet)
        behavior.state = SideSheetBehavior.STATE_HIDDEN
        overlay = findViewById(R.id.side_sheet_overlay)
        overlay.visibility = View.GONE
        val sessionManager = SessionManager(this)
        val token = sessionManager.getToken();
        val email = sessionManager.getEmail()
        print(token)
        RetrofitClient.createService(UserService::class.java).getUsuarioByEmail(email?: "","Bearer "+ token ?: "").enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {

                    user = response.body() ?: User("Usuario", "xxxxxxxxxx","sksdkdjsakdsaj","32112321321","01/01/2001","0")
                    val usernameSideSheet = findViewById<TextView>(R.id.username_side_sheet)
                    usernameSideSheet.setText(user.username)
                    println(user)
                    print(response)

                } else {
                    println("Erro: ${response.code()}")
                    startActivity(Intent(this@HomeActivity, WelcomeActivity::class.java))

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })


        profileIcon.setOnClickListener {
            behavior.state = SideSheetBehavior.STATE_EXPANDED
            overlay.visibility = View.VISIBLE
        }
        overlay.setOnClickListener {
            behavior.state = SideSheetBehavior.STATE_HIDDEN
            overlay.visibility = View.GONE

        }

        bottom_navigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.navigation_home -> {
                    InDevelopmentFragment()
                }
                R.id.navigation_projects -> {
                    ProjectsFragment()
                }
                R.id.navigation_new -> {
                    InDevelopmentFragment()
                }
                R.id.navigation_bug -> {
                    InDevelopmentFragment()
                }
                R.id.navigation_notifications -> {
                    InDevelopmentFragment()
                }
                else -> {
                    InDevelopmentFragment()
                }
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()

            true
        }


        behavior.addCallback(object : SideSheetCallback() {
            override fun onStateChanged(sheet: View, newState: Int) {
                if (newState == SideSheetBehavior.STATE_EXPANDED) {
                    overlay.visibility = View.VISIBLE
                } else if (newState == SideSheetBehavior.STATE_HIDDEN) {
                    overlay.visibility = View.GONE
                }
            }

            override fun onSlide(sheet: View, slideOffset: Float) {
            }
        })



    }
}