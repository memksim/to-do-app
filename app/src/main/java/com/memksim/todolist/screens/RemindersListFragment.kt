
package com.memksim.todolist.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.memksim.todolist.R
import com.memksim.todolist.adapters.DaysAdapter
import com.memksim.todolist.adapters.RemindersAdapter
import com.memksim.todolist.contracts.RemindersListFragmentNavigation
import com.memksim.todolist.databinding.FragmentRemindersListBinding
import com.memksim.todolist.viewmodels.DaysListViewModel
import com.memksim.todolist.viewmodels.RemindersListViewModel

class RemindersListFragment: Fragment(R.layout.fragment_reminders_list), RemindersListFragmentNavigation {

    private var _binding: FragmentRemindersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var daysListViewModel: DaysListViewModel
    private lateinit var remindersListViewModel: RemindersListViewModel

    private lateinit var navController: NavController

    private lateinit var daysAdapter: DaysAdapter
    private lateinit var remindersAdapter: RemindersAdapter

    private var clicked = false
    private var daysListHidden = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRemindersListBinding.bind(view)

        daysListViewModel = ViewModelProvider(this)[DaysListViewModel::class.java]
        remindersListViewModel = ViewModelProvider(this)[RemindersListViewModel::class.java]

        val horizontalLayoutManager = LinearLayoutManager(requireContext())
        horizontalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        daysAdapter = DaysAdapter()
        daysListViewModel.days.observe(viewLifecycleOwner, Observer {
            daysAdapter.notifyDataSetChanged()
            daysAdapter.days = it
            binding.month.text = setMonthStyle(it[0].month)
        })
        binding.daysRecyclerView.layoutManager = horizontalLayoutManager
        binding.daysRecyclerView.adapter = daysAdapter

        remindersAdapter = RemindersAdapter()
        remindersListViewModel.remindersLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("test", "remindersListViewModel.remindersLiveData")
            remindersAdapter.notifyDataSetChanged()
            remindersAdapter.reminders = it
        })
        binding.remindersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.remindersRecyclerView.adapter = remindersAdapter

        binding.moreOptions.setOnClickListener {
            moreOptionsButtonClicked(clicked)
        }

        binding.newReminder.setOnClickListener {
            openAddReminderFragment()
        }



        binding.showDaysList.setOnClickListener {
            setDaysListVisible(daysListHidden)
        }
        binding.month.setOnClickListener {
            setDaysListVisible(daysListHidden)
        }



        navController = findNavController()
    }

    private fun setDaysListVisible(isHidden: Boolean){
        if(isHidden){
            binding.daysRecyclerView.visibility = View.VISIBLE
            binding.showDaysList.setImageResource(R.drawable.ic_arrow_up)
            daysListHidden = false
        }else{
            binding.daysRecyclerView.visibility = View.GONE
            binding.showDaysList.setImageResource(R.drawable.ic_arrow_down)
            daysListHidden = true
        }

    }

    private fun moreOptionsButtonClicked(clicked: Boolean){
        setVisibility(clicked)
        this.clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked){
            binding.newReminder.visibility = View.VISIBLE
            binding.clearAll.visibility = View.VISIBLE
        }else{
            binding.newReminder.visibility = View.INVISIBLE
            binding.clearAll.visibility = View.INVISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        daysListViewModel.updateDays()
        remindersListViewModel.updateList()
    }

    override fun openAddReminderFragment() {
        navController.navigate(R.id.action_remindersListFragment_to_addReminderFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMonthStyle(month: String):String{
        return when(month){
            "Jan" -> getString(R.string.Jan)
            "Feb" -> getString(R.string.Feb)
            "Mar" -> getString(R.string.Mar)
            "Apr" -> getString(R.string.Apr)
            "May" -> getString(R.string.May)
            "Jun" -> getString(R.string.Jun)
            "Jul" -> getString(R.string.Jul)
            "Aug" -> getString(R.string.Aug)
            "Sep" -> getString(R.string.Sep)
            "Oct" -> getString(R.string.Oct)
            "Nov" -> getString(R.string.Nov)
            "Dec" -> getString(R.string.Dec)
            else -> ""
        }
    }
}