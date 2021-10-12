package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

interface AddReminderViewModelContract {

    fun createReminder(reminder: Reminder)

}