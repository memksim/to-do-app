package com.memksim.todolist.database

import androidx.room.*
import com.memksim.todolist.objects.Reminder

@Dao
interface ReminderDao {

    @Insert
    fun addNewReminder(reminder: Reminder)

    @Update
    fun updateExistingReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders")
    fun getRemindersList(): List<Reminder>

    @Query("SELECT * FROM reminders WHERE _id = :id")
    fun getReminderById(id: Int): Reminder

    @Delete
    fun deleteReminder(reminder: Reminder)

}