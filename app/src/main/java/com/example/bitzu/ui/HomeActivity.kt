package com.example.bitzu.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitzu.R
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetCallback

class HomeActivity : AppCompatActivity() {
    lateinit var profileIcon: ImageView;
    lateinit var overlay: View;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        profileIcon = findViewById(R.id.profile_icon)
        val sideSheet = findViewById<View>(R.id.modal_side_sheet)
        val behavior = SideSheetBehavior.from(sideSheet)
        behavior.state = SideSheetBehavior.STATE_HIDDEN
        overlay = findViewById(R.id.side_sheet_overlay)
        overlay.visibility = View.GONE

        profileIcon.setOnClickListener {
            behavior.state = SideSheetBehavior.STATE_EXPANDED
            overlay.visibility = View.VISIBLE
        }
        overlay.setOnClickListener {
            behavior.state = SideSheetBehavior.STATE_HIDDEN
            overlay.visibility = View.GONE

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