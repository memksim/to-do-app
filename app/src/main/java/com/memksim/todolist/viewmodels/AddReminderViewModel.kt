package com.memksim.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.memksim.todolist.contracts.AddReminderViewModelContract
import com.memksim.todolist.model.ReminderService
import com.memksim.todolist.objects.Reminder

class AddReminderViewModel(
    application: Application
): AndroidViewModel(application), AddReminderViewModelContract {

    private val service = ReminderService(application)

    override fun createReminder(reminder: Reminder) {
        service.addReminder(reminder)
        Log.d("test", "AddReminderViewModel createReminder(reminder: Reminder)")
    }
}