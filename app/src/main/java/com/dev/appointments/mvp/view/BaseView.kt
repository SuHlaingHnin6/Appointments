package com.dev.appointments.mvp.view

interface BaseView {
    fun init()
    fun setUpAdapter()
    fun setUpLoadingDialog()
    fun checkNetwork()
    fun listener()
    fun showLoading()
    fun hideLoading()
    fun showErrorMessage(message:String)
}