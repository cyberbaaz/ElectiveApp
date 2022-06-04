package com.example.elective_app

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import java.sql.*

class profile_user : AppCompatActivity() {

    lateinit var textInputLayout:TextInputLayout
//    lateinit var name:TextView
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var nameTv:TextView

//    lateinit var name:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

//        name=findViewById(R.id.usrName)
        nameTv=findViewById(R.id.welcome)
//        if (TextUtils.isEmpty(name.text.toString())){
        nameTv.text="Kshitij"
//        }
//        name=findViewById(R.id.welcome)

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

        var open_electives= arrayListOf<String>("CSEN101","CSEN102","CSEN103","CSEN104")
        var prof_elec1=arrayListOf<String>("ECEN101","MECH102","HMTS103","AEIE104")
//        val prof_elec2=arrayListOf<String>("ECEN","MECH","HMTS","AEIE")



        var arrayAdapter = ArrayAdapter(this, R.layout.list_item, open_electives)
        var arrayAdapter1 = ArrayAdapter(this, R.layout.list_item, prof_elec1)
//        val arrayAdapter2 = ArrayAdapter(this, R.layout.list_item, prof_elec2)

        var electiveNames: ArrayList<String> = ArrayList()
        var subjects: ArrayList<ArrayList<String>> = ArrayList()
        ConnectDB(electiveNames,subjects,open_electives,prof_elec1,arrayAdapter,arrayAdapter1).execute()

        // get reference to the autocomplete text view
        var autocompleteTV = findViewById<AutoCompleteTextView>(R.id.open_drop1)
        var autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.open_drop2)
        var autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.open_drop3)
        var autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.prof1_drop1)
        var autocompleteTV4 = findViewById<AutoCompleteTextView>(R.id.prof1_drop2)
        var autocompleteTV5 = findViewById<AutoCompleteTextView>(R.id.prof1_drop3)
//        val autocompleteTV6 = findViewById<AutoCompleteTextView>(R.id.prof2_drop1)
//        val autocompleteTV7 = findViewById<AutoCompleteTextView>(R.id.prof2_drop2)
//        val autocompleteTV8 = findViewById<AutoCompleteTextView>(R.id.prof2_drop3)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
        autocompleteTV1.setAdapter(arrayAdapter)
        autocompleteTV2.setAdapter(arrayAdapter)
        autocompleteTV3.setAdapter(arrayAdapter1)
        autocompleteTV4.setAdapter(arrayAdapter1)
        autocompleteTV5.setAdapter(arrayAdapter1)
//        autocompleteTV6.setAdapter(arrayAdapter2)
//        autocompleteTV7.setAdapter(arrayAdapter2)
//        autocompleteTV8.setAdapter(arrayAdapter2)
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


class ConnectDB(arglist: ArrayList<String>,argSubjects:ArrayList<ArrayList<String>>,argopen_electives:ArrayList<String>,argprof_elec1:ArrayList<String>,argarrayAdapter: ArrayAdapter<String>,argarrayAdapter1: ArrayAdapter<String>) : AsyncTask<Void, Void, String>() {
    var list: ArrayList<String> = arglist
    var subjectList: ArrayList<ArrayList<String>> = argSubjects
    var open_electives: ArrayList<String> = argopen_electives
    var prof_elec1: ArrayList<String> = argprof_elec1
    var arrayAdapter: ArrayAdapter<String> = argarrayAdapter
    var arrayAdapter1: ArrayAdapter<String> = argarrayAdapter1
//    val prof_elec2: arrayListOf<String> = arg
    override fun doInBackground(vararg params: Void?): String? {
        // ...
        var conn: Connection? = null


        var stmt: Statement? = null
        var rs: ResultSet? = null


        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver")
            conn = DriverManager.getConnection(
                "jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6497720",
                "sql6497720", "Djd8v9mdmj"
            )
            stmt = conn.createStatement()
            rs = stmt.executeQuery("select distinct(Electivename) from ElectiveSubjects where Semester=5 and Year=2022;")
            var records = ""
            while (rs.next()) {
                records += """
                ${rs.getString(1)}
                
                """.trimIndent()
                list.add(rs.getString(1))
                var subjectqry: Statement? = null
                subjectqry = conn.createStatement();
                var subjectsRes: ResultSet? = null
                subjectsRes = subjectqry.executeQuery("select PCode from ElectiveSubjects where Semester=5 and Year=2022 and ElectiveName='"+ rs.getString(1) + "';")
                var subjects:ArrayList<String> = arrayListOf()
                while(subjectsRes.next()) {
                    println(subjectsRes.getString(1))
                    subjects.add(subjectsRes.getString(1))
                }
                subjectList.add(subjects)

            }
            println("Results ------------------ $records")
            // fetch cgpa and add update button
        } catch (ex: SQLException) {
            // handle any errors
            println("SQLException: " + ex.message)
            println("SQLState: " + ex.sqlState)
            println("VendorError: " + ex.errorCode)
        } catch (ex: ClassNotFoundException) {
            println("Class not found $ex")
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close()
                } catch (sqlEx: SQLException) {
                } // ignore
                rs = null
            }
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                } // ignore
                stmt = null
            }
        }
        return null;
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // ...
//        arrayAdapter.notifyDataSetChanged()
        var i:Int = 0;
        list
        System.out.println("ELective names ------------ ")
        list.forEach{ name ->
            run {
                System.out.println(name)
                System.out.println("Subjects are ... ");
                subjectList[i].forEach { subject -> System.out.println(subject) }
                i+=1
            }
        }
        arrayAdapter.clear()
        arrayAdapter.addAll(subjectList[0])

        arrayAdapter1.clear()
        arrayAdapter1.addAll(subjectList[1])

        open_electives= subjectList[0]
        prof_elec1=subjectList[1]
//        val prof_elec2=arrayListOf<String>("ECEN","MECH","HMTS","AEIE")
        arrayAdapter.notifyDataSetChanged()
        arrayAdapter1.notifyDataSetChanged()



    }
}