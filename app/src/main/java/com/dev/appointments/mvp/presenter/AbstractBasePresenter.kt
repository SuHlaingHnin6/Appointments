package com.dev.appointments.mvp.presenter

import com.dev.appointments.mvp.view.BaseView


abstract class AbstractBasePresenter<T: BaseView> : BasePresenter<T> {

    var mView: T? = null

    override fun initPresenter(view: T) {
        mView = view
    }
}