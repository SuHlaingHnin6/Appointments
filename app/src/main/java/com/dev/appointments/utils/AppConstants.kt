package com.dev.appointments.utils

import android.content.Context
import android.content.SharedPreferences


const val BASE_URL = "http://192.168.105.156:3000"
const val Appointment = "user"


lateinit var prefs: SharedPreferences

fun loadDataFromSharedPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

}

