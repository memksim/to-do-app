package com.memksim.todolist.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.memksim.todolist.contracts.DaysListViewModelContract
import com.memksim.todolist.model.DayService
import com.memksim.todolist.objects.Day

class DaysListViewModel: ViewModel(), DaysListViewModelContract {

    private val service = DayService()

    private val _days: MutableLiveData<List<Day>> by lazy {
        MutableLiveData<List<Day>>(service.getDaysList())
    }
    var days: LiveData<List<Day>> = _days

    private fun loadDaysList(){
        _days.value = service.getDaysList()
    }

    override fun updateDays() {
        service.updateList()
        loadDaysList()
    }

}