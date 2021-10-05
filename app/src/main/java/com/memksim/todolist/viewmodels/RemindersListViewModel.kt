package com.memksim.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memksim.todolist.contracts.RemindersListViewModelContract
import com.memksim.todolist.model.Repository
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

class RemindersListViewModel(
    application: Application
): AndroidViewModel(application), RemindersListViewModelContract {

    private val repository = Repository(application)

    private val remindersMutableLiveData: MutableLiveData<List<Reminder>> by lazy {
        MutableLiveData<List<Reminder>>()
    }
    var remindersLiveData: LiveData<List<Reminder>> = remindersMutableLiveData

    private fun loadList(){
        remindersMutableLiveData.value = repository.getReminders()
        Log.d("test", "RemindersListViewModel loadList()")
    }

    override fun updateList() {
        loadList()
    }

    override fun getCategories(): List<Category> {
        return repository.getCategories()
    }

    override fun addReminder(reminder: Reminder) {
        repository.addReminder(reminder)
        loadList()
    }

    override fun updateReminder(reminder: Reminder) {
        repository.updateReminder(reminder)
        loadList()
    }

    override fun deleteReminder(position: Int) {
        val reminders = remindersLiveData.value
        repository.deleteReminder(reminders!![position])
        loadList()
    }


}