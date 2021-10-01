package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.FormattedReminder
import com.memksim.todolist.objects.Reminder

interface RepositoryContract {

    fun addReminder(reminder: Reminder)

    fun updateReminder(reminder: Reminder)

    fun getReminderById(id: Int): FormattedReminder

    fun getCategories(): List<Category>

    fun getCategory(categoryName: String): Category

    fun getReminders(): List<FormattedReminder>

    fun deleteReminder(reminder: Reminder)

}