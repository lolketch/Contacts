package com.example.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

data class DataDto(
    @SerializedName("items") val users: List<UserDto>
)

@Parcelize
data class UserDto(
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
