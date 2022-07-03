package com.example.elective_app

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import java.sql.*

class details_profile : AppCompatActivity() {
    lateinit var prof1:TextView
    lateinit var prof2:TextView
    lateinit var head:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_profile)
        var studemail:String = ""

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            System.out.println("onAuthStateChanged:signed_in:" + user.email)
            studemail = user.email.toString()

        } else {
            // User is signed out
            System.out.println("onAuthStateChanged:signed_out")
        }
        prof1=findViewById(R.id.myprof1)
        prof2=findViewById(R.id.myprof2)
        head=findViewById(R.id.username)

        getInfo(studemail,prof1,prof2,head).execute()
    }
}




class getInfo(argEmail: String, view_prof1: TextView, view_prof2: TextView, view_head: TextView) : AsyncTask<Void, Void, String>() {
    var studemail = argEmail
    var studRoll:String = ""
    var SubjectAssigned:ArrayList<String> = ArrayList()
    var ElectiveName:ArrayList<String> = ArrayList()
    var prof1=view_prof1
    var prof2=view_prof2
    var head=view_head


    //    val prof_elec2: arrayListOf<String> = arg
    @Deprecated("Deprecated in Java")
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
            rs = stmt.executeQuery("Select Roll from StudentDetails where Email='${studemail}';")
            var records = ""
            while (rs.next()) {
                studRoll = rs.getString(1)
                System.out.println("Roll studroll "+ studRoll)

            }

            System.out.println("Assigned Subjects ......... ")
            rs = stmt.executeQuery("Select Roll, p.Semester, p.PCode, ElectiveName from PreferenceDetails p inner join ElectiveSubjects e on p.PCode = e.PCode where Confirmed=1 and Roll='${studRoll}';")
            while (rs.next()) {
                System.out.println(rs.getString(3) + " " + rs.getString(4))
                SubjectAssigned.add(rs.getString(3))
                ElectiveName.add(rs.getString(4))
            }
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
        return null
    }

    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
//        SubjectAssigned.add("CSE")
//        SubjectAssigned.add("ECE")

//        println(SubjectAssigned)
        head.text="Hi, "+studemail
        if(SubjectAssigned.size>=2){
            prof1.text=SubjectAssigned[0]
            prof2.text=SubjectAssigned[1]
        }
        // Update UI here ...

    }
}
