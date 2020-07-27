package com.kirkbushman.sampleapp.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View.VISIBLE
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.MainActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.SampleApplication
import com.kirkbushman.sampleapp.doAsync
import com.kirkbushman.zammad.ZammadClient
import com.kirkbushman.zammad.models.auth.Auth
import com.kirkbushman.zammad.models.auth.AuthType
import kotlinx.android.synthetic.main.activity_login_basic.baseurl_edit
import kotlinx.android.synthetic.main.activity_login_basic.bttn_submit
import kotlinx.android.synthetic.main.activity_login_oauth.*

class LoginOAuth2Activity : AppCompatActivity() {

    val CLIENT_ID = "your_client_id"
    val CLIENT_SECRET = "your_client_secret"
    val REDIRECT_URL = "your_redirect_url"

    lateinit var authClient: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_oauth)

        val app = SampleApplication.instance

        if (app.prefs.getIsLoggedIn() && app.prefs.getLoginMode() == AuthType.OAUTH2) {
            // refresh and login
            val token = app.prefs.getToken()

            if (token != null) {
                val client = with(app.prefs) {
                    ZammadClient(
                        getBaseUrl(),
                        Auth(
                            clientID = CLIENT_ID,
                            clientSecret = CLIENT_SECRET,
                            redirectUri = REDIRECT_URL,
                            token = token,
                            type = AuthType.OAUTH2
                        ),
                        true
                    )
                }

                doAsync(
                    {
                        if (token.shouldRenew()) {
                            val newtoken = client.refreshToken()
                            app.prefs.setToken(newtoken)
                        }
                    },
                    {
                        app.setClient(client)
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                )
            }
        }

        var baseUrl = ""

        with(browser.settings) {
            allowContentAccess = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = false
        }

        browser.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

                if (authClient.isRedirectedUrl(url)) {
                    browser.stopLoading()

                    var client: ZammadClient? = null

                    doAsync(
                        doWork = {

                            client = ZammadClient.oAuth2Login(
                                baseUrl,
                                authClient,
                                url,
                                true
                            )
                        },
                        onPost = {
                            if (client != null) {
                                app.setClient(client!!)

                                with(app.prefs) {
                                    setIsLoggedIn(true)
                                    setBaseUrl(baseUrl)
                                    setToken(client!!.auth.token)
                                    setLoginMode(AuthType.OAUTH2)
                                }

                                startActivity(Intent(applicationContext, MainActivity::class.java))
                            }
                        }
                    )
                }
            }
        }

        bttn_submit.setOnClickListener {

            baseUrl = baseurl_edit.text.trim().toString()

            if (baseUrl == "") {
                Toast.makeText(this, "baseurl not found!", Toast.LENGTH_SHORT).show()
            }

            authClient = Auth(
                AuthType.OAUTH2,
                clientID = CLIENT_ID,
                clientSecret = CLIENT_SECRET,
                redirectUri = REDIRECT_URL
            )

            browser.visibility = VISIBLE

            browser.clearFormData()
            browser.loadUrl(authClient.getAuthorizeUrl(baseUrl))
        }
    }
}
