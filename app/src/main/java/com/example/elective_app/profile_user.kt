package com.example.elective_app

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import java.math.BigInteger
import java.sql.*
import java.util.*

var rollGlobal:String = "";

class profile_user : AppCompatActivity() {

    lateinit var textInputLayout:TextInputLayout
//    lateinit var name:TextView
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var nameTv:TextView
    var array_chosen1= arrayOf<String>("","","")
    var array_chosen2= arrayOf<String>("","")



//    lateinit var name:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        var studemail:String = "";

//        var mAuthListener = AuthStateListener { firebaseAuth ->
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                // User is signed in
                System.out.println("onAuthStateChanged:signed_in:" + user.email)
                studemail = user.email.toString();

            } else {
                // User is signed out
                System.out.println("onAuthStateChanged:signed_out")
            }
            // [START_EXCLUDE]
            //            updateUI(user)
            // [END_EXCLUDE]
//        }

//        val selections_arr = arrayListOf<String>()


//        name=findViewById(R.id.usrName)
        nameTv = findViewById(R.id.welcome) as TextView
//        if (TextUtils.isEmpty(name.text.toString())){
//        }
//        name=findViewById(R.id.welcome)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigation)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
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
                R.id.nav_item2 -> {
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



        textInputLayout = findViewById(R.id.menu_drop)

        val prof_elec1 = arrayListOf<String>("CSEN101", "CSEN102", "CSEN103", "CSEN104")
        val prof_elec2 = arrayListOf<String>("ECEN101", "MECH102", "HMTS103", "AEIE104")
        val sem = arrayListOf<Int>(3, 4, 5, 6, 7, 8)
//        val prof_elec2=arrayListOf<String>("ECEN","MECH","HMTS","AEIE")


        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, prof_elec1)
        val arrayAdapter1 = ArrayAdapter(this, R.layout.list_item, prof_elec2)
        val sem_arradapter = ArrayAdapter(this, R.layout.list_item, sem)


        var electiveNames: ArrayList<String> = ArrayList()
        var subjects: ArrayList<ArrayList<String>> = ArrayList()
        var studRoll: BigInteger = BigInteger("4545");
        ConnectDB(
            electiveNames,
            subjects,
            prof_elec1,
            prof_elec2,
            arrayAdapter,
            arrayAdapter1,
            studemail,
            studRoll
        ).execute()

        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.open_drop1)
        val autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.open_drop2)
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.open_drop3)
        val autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.prof1_drop1)
        val autocompleteTV4 = findViewById<AutoCompleteTextView>(R.id.prof1_drop2)
//        val autocompleteTV5 = findViewById<AutoCompleteTextView>(R.id.prof1_drop3)
        val sem_autocompleteTV = findViewById<AutoCompleteTextView>(R.id.sem_drop)
//        val autocompleteTV7 = findViewById<AutoCompleteTextView>(R.id.prof2_drop2)
//        val autocompleteTV8 = findViewById<AutoCompleteTextView>(R.id.prof2_drop3)
        // set adapter to the autocomplete tv to the arrayAdapter

        autocompleteTV.setAdapter(arrayAdapter)
        autocompleteTV1.setAdapter(arrayAdapter)
        autocompleteTV2.setAdapter(arrayAdapter)
        autocompleteTV3.setAdapter(arrayAdapter1)
        autocompleteTV4.setAdapter(arrayAdapter1)
//        autocompleteTV5.setAdapter(arrayAdapter1)
        sem_autocompleteTV.setAdapter(sem_arradapter)
//        autocompleteTV7.setAdapter(arrayAdapter2)
//        autocompleteTV8.setAdapter(arrayAdapter2)

        val choicebtn = findViewById<Button>(R.id.choice_submit)
        setOnClick(choicebtn, studemail);
//        choicebtn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
                    System.out.println("Roll below setonclick "+studemail);
                // Do whatever you want(str can be used here)
//            }
//        })

//        choicebtn.setOnClickListener {
//            System.out.println("Roll in onclick "+studRoll);
//            submitchoices(studRoll);
//
//        }
        sem_autocompleteTV.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val curr_sem = sem.get(p2).toString()
            }
        }
        autocompleteTV.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                array_chosen1[0] = prof_elec1.get(p2)
            }
        }
        autocompleteTV1.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                array_chosen1[1] = prof_elec1.get(p2)
            }
        }
        autocompleteTV2.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                array_chosen1[2] = prof_elec1.get(p2)
            }
        }
        autocompleteTV3.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                array_chosen2[0] = prof_elec2.get(p2)
            }
        }
        autocompleteTV4.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                array_chosen2[1] = prof_elec2.get(p2)
            }
        }

    }

    private fun setOnClick(btn: Button, email: String) {
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                System.out.println("Email in on click2 is "+email)
                // Do whatever you want(str can be used here)
                submitchoices(email);
            }
        })
    }

    fun submitchoices( email:String) {
        val set1 = HashSet(Arrays.asList(*array_chosen1))
        val set2 = HashSet(Arrays.asList(*array_chosen2))
        if(set1.size!=3 || set2.size!=2){
            Toast.makeText(this, "You have chosen one subject multiple times!!!\nPlease select all unique choices", Toast.LENGTH_SHORT).show()
        }
        else{
            startActivity(Intent(this,details_profile::class.java))
            finish()
        }
        System.out.println("Email in submit "+email)
        println("inside func submit............."+ Arrays.toString(array_chosen2)+"........+++++++++++////////////************"+ Arrays.toString(array_chosen1))
        InsertPref(
            set1,
            set2,
            email
        ).execute()
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


class ConnectDB(arglist: ArrayList<String>,argSubjects:ArrayList<ArrayList<String>>,argopen_electives:ArrayList<String>,argprof_elec1:ArrayList<String>,argarrayAdapter: ArrayAdapter<String>,argarrayAdapter1: ArrayAdapter<String>, argEmail:String, var argRoll:BigInteger) : AsyncTask<Void, Void, String>() {
    var list: ArrayList<String> = arglist
    var subjectList: ArrayList<ArrayList<String>> = argSubjects
    var open_electives: ArrayList<String> = argopen_electives
    var prof_elec1: ArrayList<String> = argprof_elec1
    var arrayAdapter: ArrayAdapter<String> = argarrayAdapter
    var arrayAdapter1: ArrayAdapter<String> = argarrayAdapter1
    var studemail = argEmail;
    var studroll:BigInteger = argRoll;
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



                var studentRoll: ResultSet? = null
                System.out.println("Email "+studemail);
                studentRoll = subjectqry.executeQuery("Select Roll from StudentDetails where Email='$studemail';");
                while(studentRoll.next()) {
                    argRoll = BigInteger(studentRoll.getString(1))
                    System.out.println("Roll studroll "+ studroll);
                }

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
        System.out.println(argRoll);
//        rollGlobal = studroll;


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




















class InsertPref(argset1:HashSet<String>,argset2:HashSet<String>,argEmail: String) : AsyncTask<Void, Void, String>() {

    var set1 = argset1;
    var set2 = argset2;
//    var roll = sturoll;
    var studemail = argEmail
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
            var subjectqry: Statement? = null
            subjectqry = conn.createStatement();
            var roll:String = ""
            var studentRoll: ResultSet? = null
            System.out.println("Email "+studemail);
            studentRoll = subjectqry.executeQuery("Select Roll from StudentDetails where Email='$studemail';");
            while(studentRoll.next()) {
                roll = studentRoll.getString(1)
                System.out.println("Roll studroll "+ roll);
            }


            stmt = conn.createStatement()
            var i =1;
            for (s in set1) {
                stmt.addBatch("Insert into PreferenceDetails values ('"+roll+"','"+s+"',5,2022,"+i+",0);");
                i=i+1
            }
            i=1;
            for (s in set2) {
                stmt.addBatch("Insert into PreferenceDetails values ('"+roll+"','"+s+"',5,2022,"+i+",0);");
                i=i+1
            }

            stmt.addBatch("Update StudentDetails set Submitted=1 where Roll='${roll}'");

            stmt.executeBatch();

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

        System.out.println("Inserted maybe");

    }
}