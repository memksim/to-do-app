package com.memksim.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memksim.todolist.contracts.RemindersListViewModelContract
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.model.Repository
import com.memksim.todolist.objects.FormattedReminder

class RemindersListViewModel(
    application: Application
): AndroidViewModel(application), RemindersListViewModelContract {

    private val service = Repository(application)

    private val remindersMutableLiveData: MutableLiveData<List<FormattedReminder>> by lazy {
        MutableLiveData<List<FormattedReminder>>()
    }
    var remindersLiveData: LiveData<List<FormattedReminder>> = remindersMutableLiveData

    private fun loadList(){
        remindersMutableLiveData.value = service.getReminders()
        Log.d("test", "RemindersListViewModel loadList()")
    }

    override fun updateList() {
        loadList()
    }

    override fun addReminder(reminder: Reminder) {
        service.addReminder(reminder)
        loadList()
    }

    override fun updateReminder(reminder: Reminder) {
        service.updateReminder(reminder)
        loadList()
    }

    override fun deleteReminder(reminder: Reminder) {
        service.deleteReminder(reminder)
        loadList()
    }


}