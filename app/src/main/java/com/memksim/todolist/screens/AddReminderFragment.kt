package com.memksim.todolist.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.memksim.todolist.R
import com.memksim.todolist.databinding.FragmentAddReminderBinding
import com.memksim.todolist.databinding.FragmentRemindersListBinding
import com.memksim.todolist.viewmodels.AddReminderViewModel

class AddReminderFragment: Fragment(R.layout.fragment_add_reminder) {

    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModelAdd: AddReminderViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddReminderBinding.bind(view)

        viewModelAdd = ViewModelProvider(this)[AddReminderViewModel::class.java]



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}