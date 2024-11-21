package com.dev.appointments.mvp.presenter

import com.dev.appointments.network.request.AppointmentRequest

interface AddAppointPresenter {

    fun addApoint(body: AppointmentRequest)
    fun showList ()
}