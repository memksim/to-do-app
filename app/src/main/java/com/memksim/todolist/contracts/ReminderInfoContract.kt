package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Reminder

interface ReminderInfoContract {

    fun getSavedReminder(reminder: Reminder)

    fun updateData(reminderId: Int)

    fun updateReminder(reminder: Reminder)

}