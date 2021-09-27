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

    private var clicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddReminderBinding.bind(view)

        viewModelAdd = ViewModelProvider(this)[AddReminderViewModel::class.java]

        binding.moreOptions.setOnClickListener {
            moreOptionsButtonClicked(clicked)
        }

    }

    private fun moreOptionsButtonClicked(clicked: Boolean){
        setVisibility(clicked)
        this.clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked){
            binding.saveReminder.visibility = View.VISIBLE
            binding.deleteReminder.visibility = View.VISIBLE
        }else{
            binding.saveReminder.visibility = View.INVISIBLE
            binding.deleteReminder.visibility = View.INVISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}