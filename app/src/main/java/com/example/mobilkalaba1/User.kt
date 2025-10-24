package com.example.mobilkalaba1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class User(
    val name: String,
    val email: String,
    val password: String
) : Serializable

@Parcelize
data class UserParcelable(
    val name: String,
    val email: String,
    val password: String
) : Parcelable
