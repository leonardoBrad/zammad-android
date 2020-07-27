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
import com.kirkbushman.zammad.models.auth.Token
import kotlinx.android.synthetic.main.activity_login_basic.baseurl_edit
import kotlinx.android.synthetic.main.activity_login_basic.bttn_submit
import kotlinx.android.synthetic.main.activity_login_token.*

class LoginTokenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_token)

        val app = SampleApplication.instance
        if (app.prefs.getIsLoggedIn() && app.prefs.getLoginMode() == AuthType.AUTH_TOKEN && app.prefs.getToken() != null) {

            app.setClient(ZammadClient(app.prefs.getBaseUrl(), AuthType.AUTH_TOKEN, app.prefs.getToken()!!, true))

            startActivity(Intent(this, MainActivity::class.java))
        }

        bttn_submit.setOnClickListener {

            val baseUrl = baseurl_edit.text.trim().toString()
            val token = access_token.text.trim().toString()

            if (baseUrl == "") {
                Toast.makeText(this, "baseurl not found!", Toast.LENGTH_SHORT).show()
            }

            if (token == "") {
                Toast.makeText(this, "token not found!", Toast.LENGTH_SHORT).show()
            }

            var me: User? = null
            val client = ZammadClient(baseUrl, AuthType.AUTH_TOKEN, Token(token, AuthType.AUTH_TOKEN.tokenType!!), true)

            doAsync(
                doWork = {
                    me = client.me()
                },
                onPost = {

                    if (me != null) {

                        app.setClient(client)

                        with(app.prefs) {
                            setIsLoggedIn(true)
                            setBaseUrl(baseUrl)
                            setToken(client.auth.token)
                            setLoginMode(AuthType.AUTH_TOKEN)
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
