package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Reminder

interface ReminderServiceContract {

    fun addReminder(reminder: Reminder)

    fun updateReminder(reminder: Reminder)

    fun getReminderById(id: Int): Reminder

    fun getReminders(): List<Reminder>

    fun deleteReminder(reminder: Reminder)

}