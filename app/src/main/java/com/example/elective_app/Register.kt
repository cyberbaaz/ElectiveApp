package com.example.elective_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

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


