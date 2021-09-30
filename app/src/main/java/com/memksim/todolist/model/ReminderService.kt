package com.memksim.todolist.model

import android.content.Context
import android.util.Log
import com.memksim.todolist.R
import com.memksim.todolist.contracts.ReminderServiceContract
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.database.ReminderDatabase
import com.memksim.todolist.objects.Category

class ReminderService(context: Context): ReminderServiceContract {

    private val database = ReminderDatabase.getDataBase(context)
    private val dao = database.remindersDao()

    override fun addReminder(reminder: Reminder) {
        dao.addNewReminder(reminder)
        Log.d("test", "ReminderService addReminder(reminder: Reminder)")
    }

    override fun updateReminder(reminder: Reminder) {
        dao.updateExistingReminder(reminder)
    }

    override fun getReminderById(id: Int): Reminder {
        return dao.getReminderById(id)
    }

    override fun getReminders(): List<Reminder> {

        //переворачиваем список чтобы вверху были новые элементы

        Log.d("test", "ReminderService getReminders()")
        return dao.getRemindersList().asReversed()

    }

    override fun deleteReminder(reminder: Reminder) {
        dao.deleteReminder(reminder)
    }

}