package com.memksim.todolist.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
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
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.objects.Repeat
import com.memksim.todolist.objects.Repeat.*
import com.memksim.todolist.viewmodels.AddReminderViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddReminderFragment: Fragment(R.layout.fragment_add_reminder) {

    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var viewModel: AddReminderViewModel

    private val today: Long = MaterialDatePicker.todayInUtcMilliseconds()
    private var chosenDate = today
    private var hour = 12
    private var min = 0

    private var categories: List<Category> = emptyList()
    private var names: List<String> = emptyList()

    private var whenRepeat: List<String> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddReminderBinding.bind(view)
        navController = findNavController()

        viewModel = ViewModelProvider(this)[AddReminderViewModel::class.java]

        whenRepeat = listOf(
            requireContext().resources.getString(R.string.never),
            requireContext().resources.getString(R.string.everyday),
            requireContext().resources.getString(R.string.everyTwoDays),
            requireContext().resources.getString(R.string.everyThreeDays),
            requireContext().resources.getString(R.string.everyFourDays),
            requireContext().resources.getString(R.string.everyFiveDays),
            requireContext().resources.getString(R.string.everySixDays),
            requireContext().resources.getString(R.string.everyWeek),
            requireContext().resources.getString(R.string.everyMonth),
            requireContext().resources.getString(R.string.everyHalfYear),
            requireContext().resources.getString(R.string.everyYear)
        )

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

        categories = viewModel.getCategoriesList()
        names = viewModel.getCategoriesNames()

        val adapterCategory = ArrayAdapter(requireContext(), R.layout.item_category_dropdown, names)
        binding.autoCompleteCategory.setAdapter(adapterCategory)

        val adapterRepeat = ArrayAdapter(requireContext(), R.layout.item_category_dropdown, whenRepeat)
        binding.autoCompleteRepeat.setAdapter(adapterRepeat)

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

        hour = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
        val timeFormat = SimpleDateFormat("HH:mm")
        binding.openTimePicker.text = timeFormat.format(date)
    }

    private fun saveReminder(){
        val reminder = Reminder(
            0,
            binding.autoCompleteCategory.text.toString(),
            binding.title.text.toString(),
            binding.addNote.text.toString(),
            chosenDate,
            hour,
            min,
            getRepeat(binding.autoCompleteRepeat.text.toString())
        )
        viewModel.createReminder(reminder)
        Log.d("test", "AddReminderFragment saveReminder()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRepeat(chosenRepeat: String): Repeat{
        return when(chosenRepeat){
            whenRepeat[1] -> EVERYDAY
            whenRepeat[2] -> EVERYTWODAYS
            whenRepeat[3] -> EVERYTHREEDAYS
            whenRepeat[4] -> EVERYFOURDAYS
            whenRepeat[5] -> EVERYFIVEDAYS
            whenRepeat[6] -> EVERYSIXDAYS
            whenRepeat[7] -> EVERYWEEK
            whenRepeat[9] -> EVERYMONTH
            whenRepeat[10] -> EVERYHALFYEAR
            whenRepeat[11] -> EVERYYEAR
            else -> NEVER
        }
    }

}