package com.noblecilla.listmaker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListEntity(
    val name: String?,
    val items: ArrayList<String> = ArrayList()
) : Parcelable
