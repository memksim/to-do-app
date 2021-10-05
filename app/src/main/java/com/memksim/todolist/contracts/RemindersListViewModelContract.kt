package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

interface RemindersListViewModelContract {

    fun updateList()

    fun getCategories(): List<Category>

    fun addReminder(reminder: Reminder)

    fun updateReminder(reminder: Reminder)

    fun deleteReminder(id: Int)

}