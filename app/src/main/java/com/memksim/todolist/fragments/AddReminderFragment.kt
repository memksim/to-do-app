package com.memksim.todolist.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.memksim.todolist.R
import com.memksim.todolist.databinding.FragmentAddReminderBinding
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.viewmodels.AddReminderViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddReminderFragment: Fragment(R.layout.fragment_add_reminder) {

    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var viewModel: AddReminderViewModel

    private val today: Long = MaterialDatePicker.todayInUtcMilliseconds()
    private var chosenDate = today
    private var hour = 12
    private var min = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddReminderBinding.bind(view)
        navController = findNavController()

        viewModel = ViewModelProvider(this)[AddReminderViewModel::class.java]

        binding.save.setOnClickListener {
            saveReminder()
            navController.navigate(R.id.action_addReminderFragment_to_remindersListFragment)
        }
        getCurrentDate()
        getCurrentTime()

        binding.openDatePicker.setOnClickListener {
            showDatePicker()
        }

        binding.openTimePicker.setOnClickListener {
            showTimePicker()
        }

    }

    private fun showDatePicker(){

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.selectDate)
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(today)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            chosenDate = it
            binding.openDatePicker.text = SimpleDateFormat("dd.MM.yyyy").format(chosenDate)
        }

        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun showTimePicker(){
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(hour)
                .setMinute(min)
                .setTitleText(R.string.selectTime)
                .build()

        picker.addOnPositiveButtonClickListener {
            hour = picker.hour
            min = picker.minute
            binding.openTimePicker.text = String.format(
                Locale.getDefault(), "%02d:%02d", hour, min)
        }

        picker.show(requireActivity().supportFragmentManager, "timePicker")
    }

    private fun getCurrentDate(){
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        binding.openDatePicker.text = dateFormat.format(Date())

    }

    private fun getCurrentTime(){
        val date = Date()
        val calendar = GregorianCalendar()

        val time = Date().time
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
        val timeFormat = SimpleDateFormat("HH:mm")
        binding.openTimePicker.text = timeFormat.format(date)
    }

    private fun saveReminder(){
        val reminder = Reminder(
            0,
            "testCategory",
            binding.title.text.toString(),
            binding.addNote.text.toString(),
            chosenDate,
            hour,
            min,
            ""
        )
        viewModel.createReminder(reminder)
        Log.d("test", "AddReminderFragment saveReminder()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}