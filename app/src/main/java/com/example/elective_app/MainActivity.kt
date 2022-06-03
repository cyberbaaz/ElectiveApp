package com.example.elective_app

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var mEmail: EditText
    lateinit var mPassword: EditText
    lateinit var progressbar: ProgressBar
    lateinit var resetpassword: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mEmail=findViewById(R.id.loginemail)
        mPassword=findViewById(R.id.loginpassword)
        progressbar=findViewById(R.id.loginprogressBar)
        resetpassword=findViewById(R.id.forgotpasswd)
        firebaseAuth= FirebaseAuth.getInstance()

        resetpassword.paintFlags=Paint.UNDERLINE_TEXT_FLAG




        if(firebaseAuth.currentUser !=null){
            startActivity(Intent(this,profile_user::class.java))
            finish()
        }
        val studbtn=findViewById<Button>(R.id.registerbtn)
        studbtn.setOnClickListener{
            val Intent= Intent(this,Register::class.java)
            startActivity(Intent)
        }

        val loginbutton=findViewById<Button>(R.id.loginbtn)
        loginbutton.setOnClickListener{
            loginuser()
        }

        resetpassword.setOnClickListener{
            val Intent= Intent(this,Reset_passwd::class.java)
            startActivity(Intent)
        }
    }
    private fun loginuser(){
        val email=findViewById<EditText>(R.id.loginemail).text.toString()
        val passwd=findViewById<EditText>(R.id.loginpassword).text.toString()
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

        //authenticate the user
        firebaseAuth.signInWithEmailAndPassword(email,passwd)
            .addOnCompleteListener(MainActivity()){ task->
                if (task.isSuccessful){
                    Toast.makeText(this,
                        "Logged in successfully",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,profile_user::class.java))
                    finish()
                }else{
                    val message= task.exception?.message
                    Toast.makeText(this,
                        "Error ! $message",
                        Toast.LENGTH_SHORT).show()
                        progressbar.setVisibility(View.GONE)
                 }
            }

    }


}
