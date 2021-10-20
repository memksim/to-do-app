package com.memksim.todolist.model

import android.content.Context
import android.util.Log
import com.memksim.todolist.R
import com.memksim.todolist.contracts.RepositoryContract
import com.memksim.todolist.database.CategoryDatabase
import com.memksim.todolist.database.ReminderDatabase
import com.memksim.todolist.objects.*
import java.text.SimpleDateFormat
import javax.inject.Inject

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
        return getReminders()[id]
    }

    override fun getCategories(): List<Category> {
         if (categoryDao.getCategoriesList().isEmpty()){
            //если список категорий пуст, то заполняем базовыми категориями
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.defaultCategory), R.color.grey, R.drawable.ic_category_inbox))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.workCategory), R.color.blue, R.drawable.ic_category_work))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.healthCategory), R.color.salad_green, R.drawable.ic_category_favorite))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.homeCategory), R.color.brown, R.drawable.ic_category_home))
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

    override fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category = category)
    }


}