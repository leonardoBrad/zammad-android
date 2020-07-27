package com.kirkbushman.zammad.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class UserAccessTokenRes(

    @Json(name = "tokens")
    val tokens: List<UserAccessTokensDetail>,

    @Json(name = "permissions")
    val permissions: List<UserTokenPermission>

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class UserAccessTokensDetail(

    @Json(name = "id")
    val id: Int,

    @Json(name = "user_id")
    val userId: Int,

    @Json(name = "action")
    val action: String,

    @Json(name = "label")
    val label: String,

    @Json(name = "preferences")
    val preferences: TokenPreferences,

    @Json(name = "last_used_at")
    val lastUsedAt: String?,

    @Json(name = "expire_at")
    val expireAt: String?,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class UserTokenPermission(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "note")
    val note: String,

    @Json(name = "preferences")
    val preferences: TokenPreferences,

    @Json(name = "active")
    val active: Boolean,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class TokenPreferences(

    @Json(name = "permission")
    val permission: List<String>? = null,

    @Json(name = "translations")
    val translations: List<String>? = null

) : Parcelable
