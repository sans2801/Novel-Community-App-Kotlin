package com.example.novelcommunity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
            actionBar?.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        val user=mAuth.currentUser
        if(user!=null)
        {
            val dashboardIntent = Intent(this, UserDashboard::class.java)
            startActivity(dashboardIntent)

        }
    }
}