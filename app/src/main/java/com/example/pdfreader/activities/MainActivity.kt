package com.example.pdfreader.activities


//import com.example.pdfreader.databinding.ActivityMainBinding
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.adapters.Adapter
import com.example.pdfreader.utils.DatabaseHelper
import com.example.pdfreader.models.Model1
import com.example.pdfreader.R
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*


 class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    private var recyclerView: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var arrayList: ArrayList<Model1>? = null
    private var adapters: Adapter? = null


    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //database helper class
        var helper = DatabaseHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM RECENTS", null)

        if (rs.moveToNext())
            Toast.makeText(applicationContext, rs.getString(2), Toast.LENGTH_LONG).show()


        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.getBoolean("checkbox", false).toString()
        prefs.getString("signature", "<unset>")
        prefs.getString("list", "<unset>")
        Toast.makeText(this, prefs.getString("signature", "<unset>").toString(), Toast.LENGTH_LONG).show()

        //val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.homeHostFragment)
        nav_view.setupWithNavController(navController)


        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

        // Drawer Navigation
        NavigationUI.setupWithNavController(navigation_view, navController)


        //navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        //recyclerView = findViewById(R.id.my_recycler_view)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,appBarConfiguration)

    }
}