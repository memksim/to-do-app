package com.memksim.todolist.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.memksim.todolist.R
import com.memksim.todolist.adapters.DaysAdapter
import com.memksim.todolist.databinding.FragmentRemindersListBinding
import com.memksim.todolist.viewmodels.DaysListViewModel

class RemindersListFragment: Fragment(R.layout.fragment_reminders_list) {

    private var binding: FragmentRemindersListBinding? = null
    private lateinit var viewModel: DaysListViewModel
    private lateinit var daysAdapter: DaysAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test", "фрагмент создан")
        binding = FragmentRemindersListBinding.bind(view)

        viewModel = ViewModelProvider(this)[DaysListViewModel::class.java]

        val horizontalLayoutManager = LinearLayoutManager(requireContext())
        horizontalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        daysAdapter = DaysAdapter()
        viewModel.days.observe(viewLifecycleOwner, Observer {
            daysAdapter.notifyDataSetChanged()
            daysAdapter.days = it
            binding!!.month.text = setMonthStyle(it[0].month)
        })

        binding!!.daysRecyclerView.layoutManager = horizontalLayoutManager
        binding!!.daysRecyclerView.adapter = daysAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateDays()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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