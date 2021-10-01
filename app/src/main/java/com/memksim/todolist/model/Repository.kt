package com.memksim.todolist.model

import android.content.Context
import android.util.Log
import com.memksim.todolist.R
import com.memksim.todolist.contracts.RepositoryContract
import com.memksim.todolist.database.CategoryDatabase
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.database.ReminderDatabase
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.FormattedReminder
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

    override fun getReminderById(id: Int): FormattedReminder {
        val reminder = reminderDao.getReminderById(id)

        val format = SimpleDateFormat("dd.MM.yyyy")
        val formattedDate = format.format(reminder.date)

        val categories = categoryDao.getCategoriesList()
        //todo сделать проверку не пустой ли массив категорий
        var category = categories[0]

        for (c in categories) {
            if (reminder.category == c.name) {
                category = c
            }
        }

        return FormattedReminder(
            title = reminder.title,
            note = reminder.note,
            category = category,
            dateInMillis = reminder.date,
            formattedDate = formattedDate,
            hour = reminder.hour,
            minute = reminder.min,
            repeat = reminder.repeat
        )
    }

    override fun getCategories(): List<Category> {
        //val categories = ArrayList<Category>()
        return if (categoryDao.getCategoriesList().isEmpty()){
            /*categories.add(Category(context.resources.getString(R.string.defaultCategory), R.color.light_blue))
            categories.add(Category(context.resources.getString(R.string.workCategory), R.color.blue))
            categories.add(Category(context.resources.getString(R.string.healthCategory), R.color.salad_green))
            categories.add(Category(context.resources.getString(R.string.homeCategory), R.color.brown))*/

            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.defaultCategory), R.color.light_blue))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.workCategory), R.color.blue))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.healthCategory), R.color.salad_green))
            categoryDao.saveNewCategory(Category(context.resources.getString(R.string.homeCategory), R.color.brown))

            categoryDao.getCategoriesList()
        }else{
            categoryDao.getCategoriesList()
        }

    }

    override fun getCategory(categoryName: String): Category {
        return categoryDao.getCategory(categoryName)
    }

    override fun getReminders(): List<FormattedReminder> {
        val formattedReminders = ArrayList<FormattedReminder>()
        //переворачиваем список чтобы вверху были новые элементы
        val reminders = reminderDao.getRemindersList().asReversed()
        val format = SimpleDateFormat("dd.MM.yyyy")

        val categories = getCategories()
        //todo сделать проверку не пустой ли массив категорий
        var category = categories[0]

        for(reminder in reminders){
            val formattedDate = format.format(reminder.date)

            for (c in categories) {
                if (reminder.category == c.name) {
                    category = c
                }
            }

            val formattedReminder = FormattedReminder(
                title = reminder.title,
                note = reminder.note,
                category = category,
                dateInMillis = reminder.date,
                formattedDate = formattedDate,
                hour = reminder.hour,
                minute = reminder.min,
                repeat = reminder.repeat
            )

            formattedReminders.add(formattedReminder)
        }

        return formattedReminders
    }

    override fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

}