package com.dev.appointments.fragment
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.dev.appointments.R
import com.dev.appointments.activity.HomeActivity
import com.dev.appointments.activity.MyGoogleMap
import com.dev.appointments.utils.loadDataFromSharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddAppointmentFragment : DialogFragment(){

   lateinit var edtTitle : EditText
   lateinit var edtName : EditText
   lateinit var edtCompany : EditText
   lateinit var edtDescription : EditText
   lateinit var edtDate : TextView
   lateinit var edtTime : TextView
   lateinit var edtLocation : EditText
   lateinit var tvError:TextView
    var isShare : Boolean = false
    private var listener: AddAppointmentListener? = null

    interface AddAppointmentListener {
        fun onAppointmentAdded(
            title: String, name: String, company: String,
            description: String, date: String, time: String, location: String
        )
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the activity implements the listener
        listener = context as? AddAppointmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement AddAppointmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE) // Remove title
        return inflater.inflate(R.layout.dialog_add_appointment, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        ) // Set dialog to full screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtTitle = view.findViewById<EditText>(R.id.edt_title)
        edtName = view.findViewById<EditText>(R.id.edt_cusName)
        edtCompany = view.findViewById<EditText>(R.id.edt_company)
        edtDescription = view.findViewById<EditText>(R.id.edt_description)
        edtDate = view.findViewById<TextView>(R.id.tv_date)
        edtTime = view.findViewById<TextView>(R.id.tv_time)
        edtLocation = view.findViewById<EditText>(R.id.edt_location)
        tvError = view.findViewById<TextView>(R.id.tv_error)
        val map = view.findViewById<ImageView>(R.id.btn_map)

        map.setOnClickListener {
            val intent = Intent(requireContext(), MyGoogleMap::class.java)
            startActivity(intent)
        }

        edtDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            // Create a DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Update the TextView with the selected date
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    // Show the selected date
                    edtDate.text = "$formattedDate"
                },
                year,
                month,
                dayOfMonth
            )

            // Show the DatePickerDialog
            datePickerDialog.show()

        }

        edtTime.setOnClickListener {
                // Get the current time
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                // Create a TimePickerDialog
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { _, selectedHour, selectedMinute ->
                        // Update the TextView with the selected time
                        val selectedTime = Calendar.getInstance()
                        selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
                        selectedTime.set(Calendar.MINUTE, selectedMinute)

                        // Format the time to a readable format (HH:mm)
                        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val formattedTime = timeFormat.format(selectedTime.time)

                        // Display the selected time
                        edtTime.text = "$formattedTime"
                    },
                    hour,
                    minute,
                    true // Use 24-hour format (set to false for 12-hour format)
                )

                // Show the TimePickerDialog
                timePickerDialog.show()
    }



        view.findViewById<Button>(R.id.btn_save).setOnClickListener {
            val title = edtTitle.text.toString()
            val name = edtName.text.toString()
            val company =edtCompany.text.toString()
            val description = edtDescription.text.toString()
            val date = edtDate.text.toString()
            val time = edtTime.text.toString()
            val location = edtLocation.text.toString()

            if (title.isEmpty() || name.isEmpty() || company.isEmpty() ||
                description.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
                tvError.text = "All fields are required!"
            } else {
                if (isNetworkAvailable(requireContext())) {
                    listener?.onAppointmentAdded(title, name, company, description, date, time, location)
                    dismiss()
                    Log.e("test","network is available")
                } else {
                    isShare = true
                    Toast.makeText(context, "No network connection", Toast.LENGTH_SHORT).show()
                    Log.e("test","network is not available")
                    SaveData(title.toString(),name.toString(),company.toString(),description.toString(),date.toString(),time.toString(),location.toString())
                }
             // Close the dialog
                Log.e("test","dialog save")
            }

        }

        view.findViewById<Button>(R.id.btn_cancle).setOnClickListener {
            dismiss() // Close the dialog
        }


    }

    private fun SaveData(title:String, name:String, company:String,description: String, date: String ,time:String, location :String) {
        val sharedPreferences = requireActivity().getSharedPreferences("AppointmentPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Save data from EditText and TextView
        editor.putString("title", title)
        editor.putString("name", name)
        editor.putString("company",company)
        editor.putString("description", description)
        editor.putString("date", date) // For TextView
        editor.putString("time", time) // For TextView
        editor.putString("location", location)
        editor.putBoolean("isShare" ,isShare)

        editor.apply() // Apply changes asynchronously
        Toast.makeText(requireContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show()
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
}