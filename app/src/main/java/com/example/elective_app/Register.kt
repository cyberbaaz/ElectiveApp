package com.example.elective_app

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.sql.*

class Register : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var mEmail: EditText
    lateinit var mPassword: EditText
    lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mEmail = findViewById(R.id.usrEmail)
        mPassword = findViewById(R.id.usrPassword)
        progressbar=findViewById(R.id.progressBar)

        firebaseAuth= FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser !=null){
            startActivity(Intent(this,profile_user::class.java))
            finish()
        }

        val crtusrbtn=findViewById<Button>(R.id.btncrt)
        crtusrbtn.setOnClickListener{
            registerUser()
//            val Intent= Intent(this,Login::class.java)
//            startActivity(Intent)
        }
        val usrlgnbtn=findViewById<Button>(R.id.usrLogin)
        usrlgnbtn.setOnClickListener{
            val Intent=Intent(this,MainActivity::class.java)
            startActivity(Intent)
//            registerUser()
        }
    }
    private fun registerUser(){
        val email=findViewById<EditText>(R.id.usrEmail).text.toString()
        val passwd=findViewById<EditText>(R.id.usrPassword).text.toString()
        val roll=findViewById<EditText>(R.id.rollNumber).text.toString()
        val name=findViewById<EditText>(R.id.usrName).text.toString()
        val dept=findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()

        if (TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required")
            return
        }
        if(TextUtils.isEmpty(passwd)){
            mPassword.setError("Password is required.")
            return
        }

        if (passwd.length <6){
            mPassword.setError("Password must be >=6 characters")
            return
        }

        progressbar.setVisibility(View.VISIBLE)
        firebaseAuth.createUserWithEmailAndPassword(email,passwd)
            .addOnCompleteListener(MainActivity()){ task->
                if (task.isSuccessful){
                    // Push data into db here
                    AddStudent(roll,dept).execute()
                    Toast.makeText(this,
                        "User added successfully",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,profile_user::class.java))
                    finish()
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(email,passwd)
                        .addOnCompleteListener{mTask->
                            if(mTask.isSuccessful){

                                startActivity(Intent(this,profile_user::class.java))
                                finish()

                            }else{
                                Toast.makeText(this,
                                    task.exception!!.message,
                                    Toast.LENGTH_SHORT).show()
                            }

                        }
                    Toast.makeText(this,
                        task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }

//    else{
//        Toast.makeText(this,
//            "Email and password cannot be empty",
//            Toast.LENGTH_SHORT).show()
//    }
}
}


class AddStudent(var argRoll:String,var argDept:String) : AsyncTask<Void, Void, String>() {
    var roll:String = argRoll
    var dept:String = argDept
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
            stmt.executeUpdate("insert into studentdetails values ('$roll',0,'$dept');")

            println("Results ------------------ $rs")
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

    }
}

