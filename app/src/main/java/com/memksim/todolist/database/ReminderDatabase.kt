package com.memksim.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderDatabase: RoomDatabase() {

    abstract fun remindersDao(): ReminderDao

    companion object{
        private var INSTANCE: ReminderDatabase? = null

        fun getDataBase(context: Context): ReminderDatabase{
            val template = INSTANCE
            if(template != null) return template

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReminderDatabase::class.java,
                    "usersDatabase"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }

    }

}