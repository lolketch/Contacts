package com.example.trainee.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Users(
    val items: List<User>
)

@Parcelize
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("avatarUrl") val avatar: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("userTag") val userTag: String,
    @SerializedName("department") val department: String,
    @SerializedName("position") val position: String,
    @SerializedName("birthday") val birthday: Date,
    @SerializedName("phone") val phone: String
):Parcelable
