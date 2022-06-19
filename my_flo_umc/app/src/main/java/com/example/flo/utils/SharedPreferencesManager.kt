package com.example.flo.utils

import com.example.flo.ApplicationClass.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.flo.ApplicationClass.ApplicationClass.Companion.mSharedPreferences
//import com.example.flo.ApplicationClass.Companion.X_ACCESS_TOKEN
//import com.example.flo.ApplicationClass.Companion.mSharedPreferences


fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)