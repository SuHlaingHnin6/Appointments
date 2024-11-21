package com.dev.appointments.mvp.presenter

import android.util.Log
import com.dev.appointments.mvp.view.AddApointmentView
import com.dev.appointments.network.data.MyModel
import com.dev.appointments.network.data.MyModelIMPL
import com.dev.appointments.network.request.AppointmentRequest

class AddAppointPresenterImpl :AddAppointPresenter, AbstractBasePresenter<AddApointmentView>() {

    private val mModel: MyModel = MyModelIMPL

    override fun addApoint(body: AppointmentRequest) {
        mModel.addAppointment(
            body,
            onSuccess = {
                mView?.AddAppoint(it)
                mView?.hideLoading()
                Log.e("test_add","Add Appoint in success in presenter")
            },
            onError = {
                mView?.showErrorMessage(it)
                mView?.hideLoading()
                Log.e("test_add","cash in error in presenter")
                Log.e("test_add","${ mView?.showErrorMessage(it)}  in presenter")
            }
        )
    }

    override fun showList() {
        mModel.showList(
            onSuccess = {
                mView?.showList(it)
                mView?.hideLoading()
                Log.e("test_show","Add Appoint in success in presenter")
            },
            onError = {
                mView?.showErrorMessage(it)
                mView?.hideLoading()
                Log.e("test_add","cash in error in presenter")
            }
        )
    }

    override fun onStart() {

    }

    override fun onCreate() {
        mView?.init()
        mView?.setUpLoadingDialog()
        mView?.setUpAdapter()
        mView?.checkNetwork()
        mView?.listener()
    }

    override fun onCreateView() {

    }

    override fun onViewCreated() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
        mView = null
    }


}