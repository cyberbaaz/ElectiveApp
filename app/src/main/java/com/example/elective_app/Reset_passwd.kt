package com.example.elective_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Reset_passwd : AppCompatActivity() {
    lateinit var resetEmail:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_passwd)
        var linkbtn=findViewById<Button>(R.id.linkmailbtn)
        resetEmail=findViewById(R.id.resetEmail)
        linkbtn.setOnClickListener {
            val email:String=resetEmail.text.toString().trim{it<=' '}
            if(email.isEmpty()){
                Toast.makeText(
                    this,
                    "Please enter email address",
                    Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(
                                this,
                                "Email sent successfully to reset your password !",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }else{
                            Toast.makeText(this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}