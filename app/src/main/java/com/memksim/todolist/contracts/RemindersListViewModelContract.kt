package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Reminder

interface RemindersListViewModelContract {

    fun updateList()

    fun addReminder(reminder: Reminder)

    fun updateReminder(reminder: Reminder)

    fun deleteReminder(reminder: Reminder)

}