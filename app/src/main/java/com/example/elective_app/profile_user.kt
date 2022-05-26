package com.example.elective_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth

class profile_user : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        // get reference to the string array that we just created
        val choices=resources.getStringArray(R.array.optional)
        val choices1=resources.getStringArray(R.array.professional1)
        val choices2=resources.getStringArray(R.array.professional2)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, choices)
        val arrayAdapter1 = ArrayAdapter(this, R.layout.list_item, choices1)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.list_item, choices2)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt)
        val autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt1)
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt2)
        val autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt3)
        val autocompleteTV4 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt4)
        val autocompleteTV5 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt5)
        val autocompleteTV6 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt6)
        val autocompleteTV7 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt7)
        val autocompleteTV8 = findViewById<AutoCompleteTextView>(R.id.auto_complete_txt8)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
        autocompleteTV1.setAdapter(arrayAdapter)
        autocompleteTV2.setAdapter(arrayAdapter)
        autocompleteTV3.setAdapter(arrayAdapter1)
        autocompleteTV4.setAdapter(arrayAdapter1)
        autocompleteTV5.setAdapter(arrayAdapter1)
        autocompleteTV6.setAdapter(arrayAdapter2)
        autocompleteTV7.setAdapter(arrayAdapter2)
        autocompleteTV8.setAdapter(arrayAdapter2)
    }


    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut() //logout
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}