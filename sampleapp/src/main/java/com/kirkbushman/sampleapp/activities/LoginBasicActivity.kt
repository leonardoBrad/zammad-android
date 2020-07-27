package com.kirkbushman.sampleapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.MainActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.SampleApplication
import com.kirkbushman.sampleapp.doAsync
import com.kirkbushman.zammad.ZammadClient
import com.kirkbushman.zammad.models.User
import com.kirkbushman.zammad.models.auth.AuthType
import kotlinx.android.synthetic.main.activity_login_basic.*

class LoginBasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_basic)

        val app = SampleApplication.instance
        if (app.prefs.getIsLoggedIn() && app.prefs.getLoginMode() == AuthType.AUTH_BASIC) {

            app.setClient(ZammadClient(app.prefs.getBaseUrl(), app.prefs.getUsername(), app.prefs.getPassword(), true))

            startActivity(Intent(this, MainActivity::class.java))
        }

        bttn_submit.setOnClickListener {

            val baseUrl = baseurl_edit.text.trim().toString()
            val username = email_edit.text.trim().toString()
            val password = password_edit.text.trim().toString()

            if (baseUrl == "") {
                Toast.makeText(this, "baseurl not found!", Toast.LENGTH_SHORT).show()
            }

            if (username == "") {
                Toast.makeText(this, "username not found!", Toast.LENGTH_SHORT).show()
            }

            if (password == "") {
                Toast.makeText(this, "password not found!", Toast.LENGTH_SHORT).show()
            }

            var me: User? = null
            doAsync(
                doWork = {

                    me = ZammadClient.me(baseUrl, username, password, true)
                },
                onPost = {

                    if (me != null) {

                        app.setClient(ZammadClient(baseUrl, username, password, true))

                        with(app.prefs) {
                            setIsLoggedIn(true)
                            setBaseUrl(baseUrl)
                            setLoginMode(AuthType.AUTH_BASIC)
                            setUsername(username)
                            setPassword(password)
                        }

                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "Error while trying to login!", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}
