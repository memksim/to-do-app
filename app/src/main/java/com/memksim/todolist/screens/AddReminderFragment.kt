package com.memksim.todolist.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.memksim.todolist.R
import com.memksim.todolist.databinding.FragmentAddReminderBinding
import com.memksim.todolist.databinding.FragmentRemindersListBinding
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.viewmodels.AddReminderViewModel

class AddReminderFragment: Fragment(R.layout.fragment_add_reminder) {

    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var viewModel: AddReminderViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddReminderBinding.bind(view)
        navController = findNavController()

        viewModel = ViewModelProvider(this)[AddReminderViewModel::class.java]

        binding.save.setOnClickListener {
            saveReminder()
            navController.navigate(R.id.action_addReminderFragment_to_remindersListFragment)
        }

    }

    private fun saveReminder(){
        val reminder = Reminder(
            0,
            "testCategory",
            binding.title.text.toString(),
            binding.addNote.text.toString(),
            "30.09.2021",
            "18:59",
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