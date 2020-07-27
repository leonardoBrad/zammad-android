package com.kirkbushman.zammad.models.auth

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Token(
    @Json(name = "access_token")
    val token: String,

    @Json(name = "token_type")
    val tokenType: String,

    @Json(name = "expires_in")
    val expireIn: Long? = null,

    @Json(name = "refresh_token")
    val refreshToken: String? = null,

    @Json(name = "scope")
    val scope: String? = null,

    @Json(name = "created_at")
    val createdAt: Long? = null
) : Parcelable {

    fun shouldRenew(): Boolean {
        if (tokenType != AuthType.OAUTH2.tokenType)
            return false
        if (createdAt == null) {
            return false
        }

        return createdAt + (expireIn ?: 300L) <= (System.currentTimeMillis() / 1000L)
    }
}
