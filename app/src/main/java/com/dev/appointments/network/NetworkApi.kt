package com.dev.appointments.network

import com.dev.appointments.network.request.AppointmentRequest
import com.dev.appointments.network.response.AppointmentResponse
import com.dev.appointments.network.response.AppResponse
import com.dev.appointments.utils.Appointment
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi {

    @POST(Appointment)
    fun addAppointment(
        @Body body: AppointmentRequest
    ): Call <AppointmentResponse>

    @GET(Appointment)
    fun getshowList(

    ): Call <AppointmentResponse>
}