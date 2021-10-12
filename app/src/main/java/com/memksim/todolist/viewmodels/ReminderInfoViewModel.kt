package com.memksim.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memksim.todolist.contracts.ReminderInfoContract
import com.memksim.todolist.contracts.WorkWithCategories
import com.memksim.todolist.model.Repository
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

class ReminderInfoViewModel(
    application: Application
): AndroidViewModel(application), ReminderInfoContract, WorkWithCategories {

    private val repository = Repository(application)

    private val _liveData: MutableLiveData<Reminder> by lazy {
        MutableLiveData<Reminder>()
    }
    var liveData: LiveData<Reminder> = _liveData


    private fun loadReminder(id: Int) {
        _liveData.value = repository.getReminderById(id)
    }

    override fun updateData(reminderId: Int) {
        loadReminder(reminderId)
    }

    override fun updateReminder(reminder: Reminder) {
        repository.updateReminder(reminder)
    }

    override fun getCategoriesList():List<Category> {
        return repository.getCategories()
    }

    override fun getCategoriesNames(): List<String> {
        val names = ArrayList<String>()
        val categories = repository.getCategories()

        for (c in categories){
            names.add(c.name)
        }

        return names
    }


}