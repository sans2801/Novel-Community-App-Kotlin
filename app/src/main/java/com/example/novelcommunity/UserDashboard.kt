package com.example.novelcommunity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import model.userModel

class UserDashboard : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthStateListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
            actionBar?.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_user_dashboard)

        //Start of onCreate
        //Logout Button
        val button: Button = findViewById(R.id.logout_button)

        button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }

        var genreAlert = findViewById<TextView>(R.id.genre_alert)
        var settingsButton = findViewById<Button>(R.id.redirect_settings)
        genreAlert.setOnClickListener {
            var settings:Intent = Intent(this,UserSettings::class.java)
            startActivity(settings)
        }

        settingsButton.setOnClickListener {
            var settings:Intent = Intent(this,UserSettings::class.java)
            startActivity(settings)
        }




        //Async FirebaseDB calls
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //var userDetails= mutableListOf<String>()
                    //for(data in snapshot.children)
                    //    userDetails.add(data.toString())
                    var user = snapshot.getValue(userModel::class.java)
                    findViewById<TextView>(R.id.detail_email).text = user!!.email
                    findViewById<TextView>(R.id.detail_username).text = user!!.username

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        var genre: DatabaseReference=  FirebaseDatabase.getInstance().reference.child("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid.toString()).child("Genres")

        genre.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var genre_alert=findViewById<TextView>(R.id.genre_alert)
                    genre_alert.text=""
                    genre_alert.setPadding(0,0,0,0)
                    genre_alert.setBackgroundColor(Color.WHITE)
                    genre_alert.visibility=View.GONE
                }
                else{
                    var genre_alert=findViewById<TextView>(R.id.genre_alert)
                    genre_alert.visibility=View.VISIBLE
                    genre_alert.text="Let us serve you better by knowing your preffered genre! "
                    genre_alert.setPadding(5,5,5,5)
                    genre_alert.setBackgroundColor(Color.parseColor("#ffafaf"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}