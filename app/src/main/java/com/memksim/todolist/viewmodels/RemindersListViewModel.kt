package com.memksim.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memksim.todolist.contracts.RemindersListViewModelContract
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.model.ReminderService

class RemindersListViewModel(
    application: Application
): AndroidViewModel(application), RemindersListViewModelContract {

    private val service = ReminderService(application)

    private val remindersMutableLiveData: MutableLiveData<List<Reminder>> by lazy {
        MutableLiveData<List<Reminder>>()
    }
    var remindersLiveData: LiveData<List<Reminder>> = remindersMutableLiveData

    private fun loadList(){
        remindersMutableLiveData.value = service.getReminders()
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