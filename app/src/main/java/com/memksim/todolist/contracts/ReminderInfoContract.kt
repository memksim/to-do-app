package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Reminder

interface ReminderInfoContract {

    fun updateData(reminderId: Int)

    fun updateReminder(reminder: Reminder)

}