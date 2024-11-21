package com.dev.appointments.network.data

import com.dev.appointments.network.request.AppointmentRequest
import com.dev.appointments.network.response.AppointmentResponse

interface MyModel {
fun addAppointment(
    body : AppointmentRequest,
    onSuccess: (AppointmentResponse) -> Unit,
    onError: (String) -> Unit
)

fun showList(
    onSuccess :(AppointmentResponse) ->Unit,
    onError :(String)->Unit
)
}