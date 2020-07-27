package com.kirkbushman.zammad.models.auth

import android.util.Base64
import com.kirkbushman.zammad.utils.Utils.addParamsToUrl
import com.kirkbushman.zammad.utils.Utils.generateRandomString

class Auth(
    val type: AuthType,
    var token: Token? = null,
    val username: String? = null,
    val password: String? = null,
    val clientID: String? = null,
    val clientSecret: String? = null,
    val redirectUri: String? = null,
    val state: String = generateRandomString(),
    val scope: String? = null
) {
    companion object {
        const val HEADER_AUTH = "Authorization"
        const val AUTHORIZE_PATH = "/oauth/authorize"
    }

    fun getAuthorizeUrl(baseUrl: String): String? {
        if (type == AuthType.OAUTH2) {
            val params = arrayOf(
                "client_id=$clientID",
                "response_type=code",
                "state=$state",
                "redirect_uri=$redirectUri"
            )
            return addParamsToUrl(baseUrl + AUTHORIZE_PATH, params)
        }
        return null
    }

    fun isRedirectedUrl(url: String?): Boolean {

        if (type == AuthType.OAUTH2) {

            if (url == null) {
                throw IllegalStateException("Provided url is null!")
            }

            if (redirectUri.isNullOrBlank()) {
                throw IllegalStateException("Redirect Url was not provided or invalid!")
            }

            return url.startsWith(redirectUri) && !url.endsWith(redirectUri)
        }

        return false
    }

    fun getHeader(): HashMap<String, String> {
        return hashMapOf(
            HEADER_AUTH to type.header.plus(
                when (type) {
                    AuthType.AUTH_BASIC -> {
                        String(
                            Base64.encode(
                                "$username:$password".toByteArray(),
                                Base64.NO_WRAP
                            )
                        )
                    }
                    else -> {
                        token?.token
                    }
                }
            )
        )
    }
}
