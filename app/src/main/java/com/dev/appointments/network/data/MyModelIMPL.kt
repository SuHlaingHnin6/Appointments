package com.dev.appointments.network.data

import android.util.Log
import com.dev.appointments.network.request.AppointmentRequest
import com.dev.appointments.network.response.AppointmentResponse
import retrofit2.Call
import retrofit2.Response

object MyModelIMPL: BaseModel(), MyModel {
    override fun addAppointment(
        body: AppointmentRequest,
        onSuccess: (AppointmentResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val addApointment = mNetworkApi.addAppointment(body)
        addApointment.enqueue(object : retrofit2.Callback<AppointmentResponse> {
            override fun onResponse(
                call: Call<AppointmentResponse>,
                response: Response<AppointmentResponse>
            ) {
                Log.e("test_add","Test add ")
                if (response.isSuccessful) {
                    val mResponse = response.body()
                    Log.e("test_add","Add apointment Response issuccessful in modelIMPL")
                    if (mResponse != null) {
                        onSuccess(mResponse)
                    } else {
                        onError(response.message())
                    }
                } else {
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                onError(t.message.toString())
                Log.e("test_add","Add appointment Response onFailure in MyModelImpl")
                Log.e("test_add","${t.message.toString()}")
            }

        })
    }


    override fun showList(onSuccess: (AppointmentResponse) -> Unit, onError: (String) -> Unit) {
        val showList = mNetworkApi.getshowList()
        showList.enqueue(object : retrofit2.Callback<AppointmentResponse> {
            override fun onResponse(
                call: Call<AppointmentResponse>,
                response: Response<AppointmentResponse>
            ) {
                Log.e("test_show","Test show list ")
                if (response.isSuccessful) {
                    val mResponse = response.body()
                    Log.e("test_show","show list Response issuccessful in modelIMPL")
                    if (mResponse != null) {
                        onSuccess(mResponse)
                    } else {
                        onError(response.message())
                    }
                } else {
                    onError(response.message())
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                onError(t.message.toString())
                Log.e("test_show","show appointment Response onFailure in MyModelImpl")
            }

        })
    }
}