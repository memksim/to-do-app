package com.memksim.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.memksim.todolist.contracts.AddReminderViewModelContract
import com.memksim.todolist.model.Repository
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

class AddReminderViewModel(
    application: Application
): AndroidViewModel(application), AddReminderViewModelContract {

    private val service = Repository(application)

    override fun createReminder(reminder: Reminder) {
        service.addReminder(reminder)
        Log.d("test", "AddReminderViewModel createReminder(reminder: Reminder)")
    }

    override fun getCategoriesList():List<Category> {
        return service.getCategories()
    }

    override fun getCategoriesNames(): List<String> {
        val names = ArrayList<String>()
        val categories = service.getCategories()

        for (c in categories){
            names.add(c.name)
        }

        return names
    }
}