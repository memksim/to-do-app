package com.memksim.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.memksim.todolist.contracts.AddReminderViewModelContract
import com.memksim.todolist.contracts.WorkWithCategories
import com.memksim.todolist.model.Repository
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

class AddReminderViewModel(
    application: Application
): AndroidViewModel(application), AddReminderViewModelContract, WorkWithCategories {

    private val repository = Repository(application)

    override fun createReminder(reminder: Reminder) {
        repository.addReminder(reminder)
        //Log.d("test", "AddReminderViewModel createReminder(reminder: Reminder)")
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