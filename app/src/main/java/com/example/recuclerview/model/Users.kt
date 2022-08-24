package com.example.recuclerview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var id: Int,
    var name: String,
    val department: String,
    val email:String,
    val more:String
) : Parcelable
