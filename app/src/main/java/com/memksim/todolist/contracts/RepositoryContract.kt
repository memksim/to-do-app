package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

interface RepositoryContract {

    fun addReminder(reminder: Reminder)

    fun updateReminder(reminder: Reminder)

    //получает ReminderForDatabase и конвертирует его в ReminderForView
    fun getReminderById(id: Int): Reminder

    fun getCategories(): List<Category>

    fun getCategory(categoryName: String): Category

    fun getReminders(): List<Reminder>

    fun deleteReminder(reminder: Reminder)

}