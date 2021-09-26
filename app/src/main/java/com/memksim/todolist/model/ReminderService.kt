package com.memksim.todolist.model

import android.content.Context
import com.memksim.todolist.contracts.ReminderServiceContract
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.database.ReminderDatabase

class ReminderService(context: Context): ReminderServiceContract {

    private val database = ReminderDatabase.getDataBase(context)
    private val dao = database.remindersDao()

    override fun addReminder(reminder: Reminder) {
        dao.addNewReminder(reminder)
    }

    override fun updateReminder(reminder: Reminder) {
        dao.updateExistingReminder(reminder)
    }

    override fun getReminderById(id: Int): Reminder {
        return dao.getReminderById(id)
    }

    override fun getReminders(): List<Reminder> {
        return dao.getRemindersList()
    }

    override fun deleteReminder(reminder: Reminder) {
        dao.deleteReminder(reminder)
    }

}