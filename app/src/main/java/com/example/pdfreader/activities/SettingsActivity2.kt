package com.example.pdfreader.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.pdfreader.R

class SettingsActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.getBoolean("checkbox", false).toString()
        prefs.getString("signature", "<unset>")
        prefs.getString("list", "<unset>")

        Toast.makeText(this,prefs.getString("signature","<unset>").toString(),Toast.LENGTH_LONG).show()


        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.settings,
                SettingsFragment()
            )
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)



        }
    }
}