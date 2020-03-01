package com.gelombang.apps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gelombang.apps.R

class SplashActivity : AppCompatActivity() {

    private val time:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }, time)
    }
}
