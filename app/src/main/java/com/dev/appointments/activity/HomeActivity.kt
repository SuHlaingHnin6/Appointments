package com.dev.appointments.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dev.appointments.R
import com.dev.appointments.databinding.ActivityHomeBinding
import com.dev.appointments.fragment.AddAppointmentFragment
import com.dev.appointments.mvp.presenter.AddAppointPresenter
import com.dev.appointments.mvp.presenter.AddAppointPresenterImpl
import com.dev.appointments.mvp.view.AddApointmentView
import com.dev.appointments.network.request.AppointmentRequest
import com.dev.appointments.network.response.AppointmentResponse

class HomeActivity : AppCompatActivity() ,AddApointmentView,AddAppointmentFragment.AddAppointmentListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mpresenter : AddAppointPresenterImpl

    lateinit var Title : String
    lateinit var Name: String
    lateinit var Company: String
    lateinit var Description: String
    lateinit var Date: String
    lateinit var Time: String
    lateinit var Location: String
    var isShare : Boolean =false

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        enableEdgeToEdge()

        binding.btnAdd.setOnClickListener {
            val addAppointmentFragment = AddAppointmentFragment()
            addAppointmentFragment.show(supportFragmentManager, "AddAppointmentFragment")
        }

        setUpPresenter()
        mpresenter.onCreate()
        loadDataFromSharedPreferences()

        if (isNetworkAvailable(this)){
            if (isShare){


                mpresenter.addApoint(AppointmentRequest(Title,Name,Company,Description,Date,Time,Location))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mpresenter.onDestroy()
    }

    private fun setUpPresenter() {
        mpresenter = AddAppointPresenterImpl()
        mpresenter.initPresenter(this)
    }


    override fun AddAppoint(response: AppointmentResponse) {
        val data = response.date


    }

    override fun showList(response: AppointmentResponse) {

    }

    override fun init() {

    }

    override fun setUpAdapter() {

    }

    override fun setUpLoadingDialog() {

    }

    override fun checkNetwork() {

    }

    override fun listener() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showErrorMessage(message: String) {

    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun loadDataFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences("AppointmentPrefs", Context.MODE_PRIVATE)

        Title = sharedPreferences.getString("title", "No Title").toString()
        Name = sharedPreferences.getString("name", "No Name").toString()
        Company = sharedPreferences.getString("company", "No Company").toString()
        Description = sharedPreferences.getString("description", "No Description").toString()
        Date = sharedPreferences.getString("date", "No Date").toString()
        Time = sharedPreferences.getString("time", "No Time").toString()
        Location = sharedPreferences.getString("location", "No Location").toString()
        isShare = sharedPreferences.getBoolean("isShare",false)
    }

    override fun onAppointmentAdded(
        title: String,
        name: String,
        company: String,
        description: String,
        date: String,
        time: String,
        location: String
    ) {
        Toast.makeText(this, " $title at $name", Toast.LENGTH_LONG).show()
        Log.e("test", "$title , $name, $company")
         Title = title
        Name = name
        Company = company
        Description = description
        Date = date
        Time = time
        Location = location

        mpresenter.addApoint(AppointmentRequest(Title,Name,Company,Description,Date,Time,Location))

    }
}