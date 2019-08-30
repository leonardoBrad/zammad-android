package com.kirkbushman.zammad.models

import android.os.Parcelable
import com.kirkbushman.zammad.models.base.Identifiable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Tag(

    @Json(name = "id")
    override val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "count")
    val count: Int

) : Parcelable, Identifiable {

    override fun hashCode(): Int = id
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tag

        if (id != other.id) return false

        return true
    }
}