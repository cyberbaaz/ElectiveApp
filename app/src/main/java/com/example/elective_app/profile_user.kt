package com.example.elective_app

import android.content.Intent
import android.os.AsyncTask
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
import java.sql.*

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

        var electiveNames: ArrayList<String> = ArrayList()
        var subjects: ArrayList<ArrayList<String>> = ArrayList()
        ConnectDB(electiveNames,subjects).execute()
    }


    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut() //logout
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}


class ConnectDB(arglist: ArrayList<String>,argSubjects:ArrayList<ArrayList<String>>) : AsyncTask<Void, Void, String>() {
    var list: ArrayList<String> = arglist
    var subjectList: ArrayList<ArrayList<String>> = argSubjects
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
                "jdbc:mysql://192.168.0.3:3306/elective",
                "sayantan", "sayantan"
            )
            stmt = conn.createStatement()
            rs = stmt.executeQuery("select distinct(Electivename) from electivesubjects where Semester=5 and Year=2022;")
            var records = ""
            while (rs.next()) {
                records += """
                ${rs.getString(1)}
                
                """.trimIndent()
                list.add(rs.getString(1))
                var subjectqry: Statement? = null
                subjectqry = conn.createStatement();
                var subjectsRes: ResultSet? = null
                subjectsRes = subjectqry.executeQuery("select PCode from electivesubjects where Semester=5 and Year=2022 and ElectiveName='"+ rs.getString(1) + "';")
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
    }
}