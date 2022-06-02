package com.example.elective_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class profile_user : AppCompatActivity() {

    lateinit var textInputLayout:TextInputLayout
    lateinit var autoCompleteTextView:AutoCompleteTextView
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigation)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                R.id.nav_item1 -> {
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                    logout()
                    true
                }
                R.id.nav_item2-> {
                    Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_item3 -> {
                    Toast.makeText(this, "Appeal", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }

            }
        }



        textInputLayout=findViewById(R.id.menu_drop)

        val open_electives= arrayListOf<String>("CSEN101","CSEN102","CSEN103","CSEN104")
        val prof_elec1=arrayListOf<String>("ECEN101","MECH102","HMTS103","AEIE104")
        val prof_elec2=arrayListOf<String>("ECEN","MECH","HMTS","AEIE")

        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, open_electives)
        val arrayAdapter1 = ArrayAdapter(this, R.layout.list_item, prof_elec1)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.list_item, prof_elec2)


        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.open_drop1)
        val autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.open_drop2)
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.open_drop3)
        val autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.prof1_drop1)
        val autocompleteTV4 = findViewById<AutoCompleteTextView>(R.id.prof1_drop2)
        val autocompleteTV5 = findViewById<AutoCompleteTextView>(R.id.prof1_drop3)
        val autocompleteTV6 = findViewById<AutoCompleteTextView>(R.id.prof2_drop1)
        val autocompleteTV7 = findViewById<AutoCompleteTextView>(R.id.prof2_drop2)
        val autocompleteTV8 = findViewById<AutoCompleteTextView>(R.id.prof2_drop3)
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


    fun logout() {
        FirebaseAuth.getInstance().signOut() //logout
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}