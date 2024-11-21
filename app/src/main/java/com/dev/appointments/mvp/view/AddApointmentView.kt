package com.dev.appointments.mvp.view

import com.dev.appointments.network.response.AppointmentResponse

interface AddApointmentView : BaseView {

    fun AddAppoint(response: AppointmentResponse)
    fun showList(response: AppointmentResponse)
}