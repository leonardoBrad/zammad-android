package com.kirkbushman.zammad.models.auth

enum class AuthType(val header: String, val tokenType: String?) {
    AUTH_BASIC("Basic ", null),
    AUTH_TOKEN("Token token=", "access_token"),
    OAUTH2("Bearer ", "Bearer")
}
