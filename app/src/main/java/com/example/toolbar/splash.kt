package com.example.toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class splash : AppCompatActivity() {

    private val splashTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        CoroutineScope(Dispatchers.Main).launch {
            delay(splashTime)

            val intent  = Intent (this@splash,
                MainActivity::class.java)
            startActivity(intent)
            finish()
        }


       /* //primeira forma
        Hadler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)*/




    }
}