package com.memksim.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.memksim.todolist.R
import com.memksim.todolist.dao.CategoryDao
import com.memksim.todolist.objects.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class CategoryDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object{
        private var INSTANCE: CategoryDatabase? = null

        fun getDataBase(context: Context): CategoryDatabase{
            val template = INSTANCE
            if(template != null) return template

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryDatabase::class.java,
                    "categoriesDatabase"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }

    }

}