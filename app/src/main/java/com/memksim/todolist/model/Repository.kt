package com.memksim.todolist.model

import android.content.Context
import android.util.Log
import com.memksim.todolist.R
import com.memksim.todolist.contracts.RepositoryContract
import com.memksim.todolist.database.CategoryDatabase
import com.memksim.todolist.database.ReminderDatabase
import com.memksim.todolist.objects.*
import java.text.SimpleDateFormat

class Repository(private val context: Context): RepositoryContract {

    private val categoryDatabase = CategoryDatabase.getDataBase(context)
    private val categoryDao = categoryDatabase.categoryDao()

    private val reminderDatabase = ReminderDatabase.getDataBase(context)
    private val reminderDao = reminderDatabase.remindersDao()

    override fun addReminder(reminder: Reminder) {
        reminderDao.saveNewReminder(reminder)
        Log.d("test", "ReminderService addReminder(reminder: Reminder)")
    }

    override fun updateReminder(reminder: Reminder) {
        reminderDao.updateExistingReminder(reminder)
    }

    override fun getReminderById(id: Int): Reminder {
        return reminderDao.getReminderById(id)
    }

    override fun getCategories(): List<Category> {
         if (categoryDao.getCategoriesList().isEmpty()){
            //если список категорий пуст, то заполняем базовыми категориями
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.defaultCategory), R.color.light_blue))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.workCategory), R.color.blue))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.healthCategory), R.color.salad_green))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.homeCategory), R.color.brown))
        }
        return categoryDao.getCategoriesList()
    }

    override fun getCategory(categoryName: String): Category {
        return categoryDao.getCategory(categoryName)
    }

    override fun getReminders(): List<Reminder> {
        //переворачиваем список чтобы вверху были новые элементы
        return reminderDao.getRemindersList().asReversed()
    }

    override fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    /*private fun getRepeat(chosenRepeat: String): Repeat {
        return when(chosenRepeat){
            context.resources.getString(R.string.everyday) -> Repeat.EVERYDAY
            context.resources.getString(R.string.everyTwoDays) -> Repeat.EVERYTWODAYS
            context.resources.getString(R.string.everyThreeDays) -> Repeat.EVERYTHREEDAYS
            context.resources.getString(R.string.everyFourDays) -> Repeat.EVERYFOURDAYS
            context.resources.getString(R.string.everyFiveDays) -> Repeat.EVERYFIVEDAYS
            context.resources.getString(R.string.everySixDays) -> Repeat.EVERYSIXDAYS
            context.resources.getString(R.string.everyWeek) -> Repeat.EVERYWEEK
            context.resources.getString(R.string.everyMonth) -> Repeat.EVERYMONTH
            context.resources.getString(R.string.everyHalfYear) -> Repeat.EVERYHALFYEAR
            context.resources.getString(R.string.everyYear) -> Repeat.EVERYYEAR
            else -> Repeat.NEVER
        }
    }

    private fun getRepeatTitle(repeat: Repeat): String{
        return when(repeat){
            Repeat.EVERYDAY -> context.resources.getString(R.string.everyday)
            Repeat.EVERYTWODAYS -> context.resources.getString(R.string.everyTwoDays)
            Repeat.EVERYTHREEDAYS -> context.resources.getString(R.string.everyThreeDays)
            Repeat.EVERYFOURDAYS -> context.resources.getString(R.string.everyFourDays)
            Repeat.EVERYFIVEDAYS -> context.resources.getString(R.string.everyFiveDays)
            Repeat.EVERYSIXDAYS -> context.resources.getString(R.string.everySixDays)
            Repeat.EVERYWEEK -> context.resources.getString(R.string.everyWeek)
            Repeat.EVERYMONTH -> context.resources.getString(R.string.everyMonth)
            Repeat.EVERYHALFYEAR -> context.resources.getString(R.string.everyHalfYear)
            Repeat.EVERYYEAR -> context.resources.getString(R.string.everyYear)
            else -> context.resources.getString(R.string.never)
        }
    }*/

}