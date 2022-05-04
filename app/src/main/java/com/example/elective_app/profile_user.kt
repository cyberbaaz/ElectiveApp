package com.example.elective_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class profile_user : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut() //logout
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}