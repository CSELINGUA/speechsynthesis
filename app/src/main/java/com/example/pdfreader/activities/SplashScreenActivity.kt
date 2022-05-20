package com.example.pdfreader.activities

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pdfreader.utils.DataInput
import com.example.pdfreader.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity(){


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splash_icon.alpha = 0f

        splash_icon.animate().setDuration(500).alpha(1f).withEndAction {
            val i = Intent(this, DataInput::class.java)
            startActivity(i)
            finish()

        }
    }
}
