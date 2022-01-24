package com.example.trainee.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchParams(var searchText:String):Parcelable
