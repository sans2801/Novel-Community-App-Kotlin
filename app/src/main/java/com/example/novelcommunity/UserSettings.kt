package com.example.novelcommunity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserSettings : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)


        var fiction : CheckBox = findViewById(R.id.fiction)
        var nonFiction : CheckBox = findViewById(R.id.nonfiction)
        var romance : CheckBox = findViewById(R.id.romance)
        var thriller : CheckBox = findViewById(R.id.thriller)
        var drama : CheckBox = findViewById(R.id.drama)
        var manga : CheckBox = findViewById(R.id.manga)
        var kids : CheckBox = findViewById(R.id.kids)
        var comic : CheckBox = findViewById(R.id.comic)
        var button : Button = findViewById(R.id.submit_genre)


        //Async FirebaseDB call
        var genre: DatabaseReference =  FirebaseDatabase.getInstance().reference.child("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Genres")

        genre.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var userGenres = mutableListOf<String>()
                    for(genre in snapshot.children)
                        userGenres.add(genre.value as String)

                    if("fiction" in userGenres) fiction.isChecked = true
                    if("nonfiction" in userGenres) nonFiction.isChecked = true
                    if("romance" in userGenres) romance.isChecked = true
                    if("thriller" in userGenres) thriller.isChecked = true
                    if("drama" in userGenres) drama.isChecked = true
                    if("fiction" in userGenres) fiction.isChecked = true
                    if("manga" in userGenres) manga.isChecked = true
                    if("kids" in userGenres) kids.isChecked = true
                    if("comic" in userGenres) comic.isChecked = true

                }
                else{

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //User Input
        button.setOnClickListener {
            var genres = mutableListOf<String>()
            if(fiction.isChecked)  genres.add("fiction")
            if(nonFiction.isChecked)  genres.add("nonfiction")
            if(romance.isChecked)  genres.add("romance")
            if(thriller.isChecked)  genres.add("thriller")
            if(drama.isChecked)  genres.add("drama")
            if(manga.isChecked)  genres.add("manga")
            if(kids.isChecked)  genres.add("kids")
            if(comic.isChecked)  genres.add("comic")

            FirebaseAuth.getInstance().currentUser?.let {

                FirebaseDatabase.getInstance().getReference("Users").child(it.uid).child("Genres").setValue(genres)
                        .addOnCompleteListener{task->
                            if(task.isSuccessful) {

                                Toast.makeText(this,"Genres Recieved!",Toast.LENGTH_SHORT).show()

                            }
                            else{
                                Toast.makeText(this, "Unsuccesful, PLease chech your connectivity :(", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }


    }
}