package com.example.trainee.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Users(
    @SerializedName("items") var items: List<User>
)

@Parcelize
data class User(
    @SerializedName("id") var id: String,
    @SerializedName("avatarUrl") var avatar: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("userTag") var userTag: String,
    @SerializedName("department") var department: String,
    @SerializedName("position") var position: String,
    @SerializedName("birthday") var birthday: Date,
    @SerializedName("phone") var phone: String
):Parcelable
