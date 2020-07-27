package com.kirkbushman.sampleapp.data

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.kirkbushman.zammad.models.auth.AuthType
import com.kirkbushman.zammad.models.auth.Token

class Preferences(context: Context) {

    companion object {

        private const val APP_SHARED_PREFS = "sample_app_sample_preferences"

        private const val FLAG_ISLOGGED = "flag_is_logged_in"
        private const val VAL_LOGIN_TYPE = "value_login_type"
        private const val VAL_BASEURL = "value_base_url"
        private const val VAL_USERNAME = "value_username"
        private const val VAL_PASSWORD = "value_password"
        private const val VAL_TOKEN = "value_token"
        private const val VAL_TOKEN_REFRESH = "value_token_refresh"
        private const val VAL_TOKEN_CREATED_AT = "value_token_created_At"
        private const val VAL_TOKEN_EXPIREIN = "value_token_expire_in"
        private const val VAL_TOKEN_TYPE = "value_token_type"
    }

    private val prefs by lazy { context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE) }

    fun getIsLoggedIn(): Boolean = prefs.getBoolean(FLAG_ISLOGGED, false)
    fun setIsLoggedIn(isLoggedIn: Boolean) {
        prefs.edit {

            putBoolean(FLAG_ISLOGGED, isLoggedIn)
        }
    }

    fun getLoginMode(): AuthType = AuthType.valueOf(prefs.getString(VAL_LOGIN_TYPE, AuthType.AUTH_BASIC.toString()) ?: AuthType.AUTH_BASIC.toString())
    fun setLoginMode(authType: AuthType) {
        prefs.edit {

            putString(VAL_LOGIN_TYPE, authType.toString())
        }
    }

    fun getBaseUrl(): String = prefs.getString(VAL_BASEURL, "") ?: ""
    fun setBaseUrl(baseUrl: String) {
        prefs.edit {

            putString(VAL_BASEURL, baseUrl)
        }
    }

    fun getUsername(): String = prefs.getString(VAL_USERNAME, "") ?: ""
    fun setUsername(username: String) {
        prefs.edit {

            putString(VAL_USERNAME, username)
        }
    }

    fun getPassword(): String = prefs.getString(VAL_PASSWORD, "") ?: ""
    fun setPassword(password: String) {
        prefs.edit {

            putString(VAL_PASSWORD, password)
        }
    }

    fun getToken(): Token? {
        val token = prefs.getString(VAL_TOKEN, null)
        val t = prefs.getString(VAL_TOKEN_TYPE, null)

        if (!token.isNullOrBlank() && t != null)
            return Token(
                token = token,
                tokenType = t,
                expireIn = prefs.getLong(VAL_TOKEN_EXPIREIN, 300),
                createdAt = prefs.getLong(VAL_TOKEN_CREATED_AT, 0),
                refreshToken = prefs.getString(VAL_TOKEN_REFRESH, null)
            )
        return null
    }

    fun setToken(token: Token?) {

        prefs.edit {
            if (token != null) {
                putString(VAL_TOKEN, token.token)
                putString(VAL_TOKEN_TYPE, token.tokenType)

                if (token.refreshToken != null)
                    putString(VAL_TOKEN_REFRESH, token.refreshToken)

                if (token.createdAt != null)
                    putLong(VAL_TOKEN_CREATED_AT, token.createdAt!!)
                if (token.expireIn != null)
                    putLong(VAL_TOKEN_EXPIREIN, token.expireIn!!)
            }
        }
    }
}
