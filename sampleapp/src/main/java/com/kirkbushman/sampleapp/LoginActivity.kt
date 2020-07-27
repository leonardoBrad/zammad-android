package com.kirkbushman.sampleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.activities.LoginBasicActivity
import com.kirkbushman.sampleapp.activities.LoginOAuth2Activity
import com.kirkbushman.sampleapp.activities.LoginTokenActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bttn_basic.setOnClickListener {
            startActivity(Intent(applicationContext, LoginBasicActivity::class.java))
        }

        bttn_token.setOnClickListener {
            startActivity(Intent(applicationContext, LoginTokenActivity::class.java))
        }

        bttn_oauth2.setOnClickListener {
            startActivity(Intent(applicationContext, LoginOAuth2Activity::class.java))
        }
    }
}
