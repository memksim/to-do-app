package com.memksim.todolist.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.memksim.todolist.R
import com.memksim.todolist.contracts.ReminderInfoContract
import com.memksim.todolist.databinding.FragmentReminderInfoBinding
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.viewmodels.ReminderInfoViewModel

class ReminderInfoFragment: Fragment(R.layout.fragment_reminder_info) {

    private var _binding: FragmentReminderInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReminderInfoViewModel

    private lateinit var navController: NavController

    private val args: ReminderInfoFragmentArgs by navArgs()

    private var categories: List<Category> = emptyList()
    private var categoriesTitles: List<String> = emptyList()

    private lateinit var reminder: Reminder
    private var whenRepeat: List<String> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReminderInfoBinding.bind(view)
        navController = findNavController()


        viewModel = ViewModelProvider(this)[ReminderInfoViewModel::class.java]
        viewModel.updateData(args.id)
        reminder = viewModel.liveData.value!!
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            reminder = it
        })

        categories = viewModel.getCategoriesList()
        categoriesTitles = sortCategoriesList(reminder.categoryTitle ,viewModel.getCategoriesNames())
        val adapterCategory = ArrayAdapter(requireContext(), R.layout.item_category_dropdown, categoriesTitles)
        binding.autoCompleteCategory.setAdapter(adapterCategory)

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
        val adapterRepeat = ArrayAdapter(requireContext(), R.layout.item_category_dropdown, whenRepeat)
        binding.autoCompleteRepeat.setAdapter(adapterRepeat)

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_reminderInfoFragment_to_remindersListFragment)
        }

        binding.title.setText(reminder.title)
        binding.openDatePicker.text = reminder.getFormattedDate()
        binding.openTimePicker.text = reminder.getFormattedTime()
        binding.addNote.setText(reminder.note)

    }

    override fun onStart() {
        super.onStart()
        viewModel.updateData(args.id)
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

}