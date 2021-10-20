package com.memksim.todolist.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.PopupMenu
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
import com.memksim.todolist.databinding.FragmentReminderBinding
import com.memksim.todolist.objects.*
import com.memksim.todolist.viewmodels.AddReminderViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddReminderFragment:
    Fragment(R.layout.fragment_reminder){

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var viewModel: AddReminderViewModel

    private var dateTimeVisible = false

    private var categories: List<Category> = emptyList()
    private var categoriesTitles: List<String> = emptyList()

    private var whenRepeat: List<String> = emptyList()

    private lateinit var reminder: Reminder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReminderBinding.bind(view)
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

        categories = viewModel.getCategoriesList()
        categoriesTitles = viewModel.getCategoriesNames()

        reminder = if(savedInstanceState != null){
            savedInstanceState.getParcelable("Reminder")!!
        }else{
            Reminder(
                0,
                title = binding.addTitle.text.toString(),
                note = binding.addNote.text.toString(),
                dateInMillis = MaterialDatePicker.todayInUtcMilliseconds(),
                hour = 12,
                minute = 0,
                repeatResId = getRepeatResId(whenRepeat[0]),
                categoryTitle = categoriesTitles[0],
                priorityLevel = 0
            )
        }

        getCurrentDate()
        getCurrentTime()

        binding.save.setOnClickListener {
            saveReminder()
            navController.navigate(R.id.action_addReminderFragment_to_remindersListFragment)
        }

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_addReminderFragment_to_remindersListFragment)
        }

        binding.addTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //до изменения текстового поля
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //во время изменения текстового поля
            }

            override fun afterTextChanged(p0: Editable?) {
                //сразу после изменения текстового поля
                reminder.title = p0.toString()
            }

        })

        binding.addNote.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //до изменения текстового поля
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //во время изменения текстового поля
            }

            override fun afterTextChanged(p0: Editable?) {
                //сразу после изменения текстового поля
                reminder.note = p0.toString()
            }

        })

        binding.addDateTime.setOnClickListener {
            if(!dateTimeVisible){
                binding.dateTime.visibility = View.VISIBLE
                binding.addDateTime.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_drop_up)
            }else{
                binding.dateTime.visibility = View.GONE
                binding.addDateTime.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_drop_down)
            }
            dateTimeVisible = !dateTimeVisible
        }

        binding.addDate.text = reminder.getFormattedDate()
        binding.addDate.setOnClickListener {
            showDatePicker()
        }

        binding.addTime.text = reminder.getFormattedTime()
        binding.addTime.setOnClickListener {
            showTimePicker()
        }

        binding.whenRepeat.setText(reminder.repeatResId)
        binding.whenRepeat.setOnClickListener {
            showPopUpMenu(it, R.menu.repeat_dropdown_menu)
        }

        setCategoryDrawable(reminder.categoryTitle)
        binding.selectCategory.text = reminder.categoryTitle
        binding.selectCategory.setOnClickListener {
            showPopUpMenu(it, R.menu.categories_dropdown_menu)
        }

        binding.selectPriority.setText(reminder.getPriorityResId())
        binding.selectPriority.setOnClickListener {
            showPopUpMenu(it, R.menu.priority_dropdown_menu)
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
                .setSelection(reminder.dateInMillis)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            reminder.dateInMillis = it
            binding.addDate.text = reminder.getFormattedDate()
        }

        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun showTimePicker(){
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(reminder.hour)
                .setMinute(reminder.minute)
                .setTitleText(R.string.selectTime)
                .build()

        picker.addOnPositiveButtonClickListener {
            reminder.hour = picker.hour
            reminder.minute = picker.minute
            binding.addTime.text = reminder.getFormattedTime()
        }

        picker.show(requireActivity().supportFragmentManager, "timePicker")
    }

    private fun getCurrentDate(){
        binding.addDate.text = reminder.getFormattedDate()
    }

    private fun getCurrentTime(){
        val calendar = GregorianCalendar()

        reminder.hour = calendar.get(Calendar.HOUR_OF_DAY)
        reminder.minute = calendar.get(Calendar.MINUTE)
        binding.addTime.text = reminder.getFormattedTime()
    }

    private fun saveReminder(){
        viewModel.createReminder(reminder)
        Log.d("test", "AddReminderFragment saveReminder()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("Reminder", reminder)
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

    private fun showPopUpMenu(v: View, @MenuRes menuRes: Int){
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        if (menuRes == R.menu.categories_dropdown_menu){
            for (i in categoriesTitles){
                popup.menu.add(i)
            }
        }

        popup.setOnMenuItemClickListener { it: MenuItem ->
            when(menuRes){
                R.menu.categories_dropdown_menu ->{
                    reminder.categoryTitle = it.title.toString()
                    binding.selectCategory.text = reminder.categoryTitle
                    setCategoryDrawable(reminder.categoryTitle)
                    true
                }
                R.menu.priority_dropdown_menu ->{
                    reminder.priorityLevel = getPriorityLevel(it.title.toString())
                    binding.selectPriority.text = it.title
                    true
                }
                R.menu.repeat_dropdown_menu ->{
                    reminder.repeatResId = getRepeatResId(it.title.toString())
                    binding.whenRepeat.text = it.title
                    true
                }
                else -> {
                    false
                }
            }

        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    private fun getPriorityLevel(title: String): Int{
        return when(title){
            requireContext().resources.getString(R.string.level1) -> 1
            requireContext().resources.getString(R.string.level2) -> 2
            requireContext().resources.getString(R.string.level3) -> 3
            else -> 0
        }
    }

    private fun setCategoryDrawable(title: String){
        val category: Category
        for (c in categories){
            if(c.name == title){
                category = c
                binding.categoryDrawable.setImageResource(category.iconResId)

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                    binding.categoryDrawable.backgroundTintList = requireContext().resources.getColorStateList(category.colorResId, null)
                }else{
                    binding.categoryDrawable.backgroundTintList = requireContext().resources.getColorStateList(category.colorResId)
                }
                break
            }
        }
    }
}