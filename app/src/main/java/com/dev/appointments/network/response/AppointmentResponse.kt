package com.dev.appointments.network.response

import com.google.gson.annotations.SerializedName

class AppointmentResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("cusName") val cusName: String,
    @SerializedName("company") val company: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("location") val location: String,
)