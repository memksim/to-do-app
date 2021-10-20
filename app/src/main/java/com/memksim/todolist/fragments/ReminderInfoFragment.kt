package com.memksim.todolist.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.memksim.todolist.R
import com.memksim.todolist.databinding.FragmentReminderBinding
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.viewmodels.ReminderInfoViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReminderInfoFragment: Fragment(R.layout.fragment_reminder) {

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReminderInfoViewModel

    private lateinit var navController: NavController

    private val args: ReminderInfoFragmentArgs by navArgs()

    private var categories: List<Category> = emptyList()
    private var categoriesTitles: List<String> = emptyList()

    private lateinit var reminder: Reminder
    private var whenRepeat: List<String> = emptyList()

    private var dateTimeVisible = false

    private var updated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReminderBinding.bind(view)
        navController = findNavController()
        viewModel = ViewModelProvider(this)[ReminderInfoViewModel::class.java]

        updateUi(savedInstanceState)

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            reminder = it
        })

        binding.save.setOnClickListener {
            done()
        }

        binding.addTitle.setText(reminder.title)
        binding.addTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //до изменения текстового поля
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //во время изменения текстового поля
            }

            override fun afterTextChanged(p0: Editable?) {
                //сразу после изменения текстового поля
                viewModel.liveData.value!!.title = p0.toString()
                updated = true
            }

        })

        binding.addNote.setText(reminder.note)
        binding.addNote.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //до изменения текстового поля
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //во время изменения текстового поля
            }

            override fun afterTextChanged(p0: Editable?) {
                //сразу после изменения текстового поля
                viewModel.liveData.value!!.note = p0.toString()
                updated = true
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

        categories = viewModel.getCategoriesList()
        categoriesTitles = sortCategoriesList(reminder.categoryTitle ,viewModel.getCategoriesNames())
        setCategoryDrawable(categoriesTitles[0])
        binding.selectCategory.text = reminder.categoryTitle
        binding.selectCategory.setOnClickListener {
            showPopUpMenu(it, R.menu.categories_dropdown_menu)
        }

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
        binding.whenRepeat.setText(reminder.repeatResId)
        binding.whenRepeat.setOnClickListener {
            showPopUpMenu(it, R.menu.repeat_dropdown_menu)
        }

        binding.selectPriority.setText(reminder.getPriorityResId())
        binding.selectPriority.setOnClickListener {
            showPopUpMenu(it, R.menu.priority_dropdown_menu)
        }

        binding.back.setOnClickListener {
            done()
        }

    }

    private fun updateUi(savedInstanceState: Bundle?){
        if (savedInstanceState != null){
            viewModel.getSavedReminder(savedInstanceState.getParcelable("Reminder")!!)
        }else{
            viewModel.updateData(args.id)
        }
        reminder = viewModel.liveData.value!!
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
            updated = true
        }

        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun showTimePicker(){
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
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
            binding.addTime.text = String.format(
                Locale.getDefault(), "%02d:%02d",
                reminder.hour, reminder.minute)
            updated = true
        }

        picker.show(requireActivity().supportFragmentManager, "timePicker")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("Reminder", reminder)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sortCategoriesList(title: String, list: List<String>): List<String>{
        val newList = ArrayList<String>(list)

        for (i in list.indices){
            if(list[i] == title){
                newList[i] = list[0]
                newList[0] = title
            }
        }

        return newList
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
                    updated = true
                    binding.selectCategory.text = it.title
                    setCategoryDrawable(it.title.toString())
                    viewModel.liveData.value!!.categoryTitle = it.title.toString()
                    true
                }
                R.menu.priority_dropdown_menu ->{
                    updated = true
                    binding.selectPriority.text = it.title
                    viewModel.liveData.value!!.priorityLevel = getPriorityLevel(it.title.toString())
                    true
                }
                R.menu.repeat_dropdown_menu ->{
                    updated = true
                    binding.whenRepeat.text = it.title
                    viewModel.liveData.value!!.repeatResId = getRepeatResId(it.title.toString())
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

    private fun done(){
        if (updated){
            viewModel.updateReminder(reminder)
            Toast.makeText(requireContext(), R.string.updated, Toast.LENGTH_SHORT).show()
        }
        navController.navigate(R.id.action_reminderInfoFragment_to_remindersListFragment)
    }
}