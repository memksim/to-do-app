package com.memksim.todolist.fragments

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
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
import com.memksim.todolist.objects.*
import com.memksim.todolist.viewmodels.AddReminderViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddReminderFragment: Fragment(R.layout.fragment_add_reminder) {

    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var viewModel: AddReminderViewModel

    private var chosenDateInMillis = MaterialDatePicker.todayInUtcMilliseconds()
    private var hour = 12
    private var min = 0

    private var categories: List<Category> = emptyList()
    private var categoriesTitles: List<String> = emptyList()

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

        binding.close.setOnClickListener {
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
        categoriesTitles = viewModel.getCategoriesNames()

        val adapterCategory = ArrayAdapter(requireContext(), R.layout.item_category_dropdown, categoriesTitles)
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
                .setSelection(chosenDateInMillis)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            chosenDateInMillis = it
            binding.openDatePicker.text = SimpleDateFormat("dd.MM.yyyy").format(chosenDateInMillis)
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
        binding.openDatePicker.text = getFormattedDate(chosenDateInMillis)
    }

    private fun getFormattedDate(dateInMillis: Long): String{
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(dateInMillis)
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
        val reminder = buildReminder()
        viewModel.createReminder(reminder)
        Log.d("test", "AddReminderFragment saveReminder()")
    }

    private fun buildReminder(): Reminder {

        return Reminder(
            0,
            title = binding.title.text.toString(),
            note = binding.addNote.text.toString(),
            categoryTitle = binding.autoCompleteCategory.text.toString(),
            dateInMillis = chosenDateInMillis,
            hour = hour,
            minute = min,
            repeatResId = getRepeatResId(binding.autoCompleteRepeat.text.toString())
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRepeatResId(repeat: String): Int{
        return when(repeat){
            resources.getString(R.string.everyday) -> R.string.everyday
            resources.getString(R.string.everyTwoDays) -> R.string.everyTwoDays
            resources.getString(R.string.everyThreeDays) -> R.string.everyThreeDays
            resources.getString(R.string.everyFourDays) -> R.string.everyFourDays
            resources.getString(R.string.everyFiveDays) -> R.string.everyFiveDays
            resources.getString(R.string.everySixDays) -> R.string.everySixDays
            resources.getString(R.string.everyWeek) -> R.string.everyWeek
            resources.getString(R.string.everyMonth) -> R.string.everyMonth
            resources.getString(R.string.everyHalfYear) -> R.string.everyHalfYear
            resources.getString(R.string.everyYear) -> R.string.everyYear
            else -> R.string.never
        }
    }

}