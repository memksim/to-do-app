package com.memksim.todolist.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.memksim.todolist.R
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "reminders")
open class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int,
    @ColumnInfo(name = "_title")val title: String,
    @ColumnInfo(name = "_note")val note: String,
    @ColumnInfo(name = "_category")val categoryTitle: String,
    @ColumnInfo(name = "_date")val dateInMillis: Long,
    @ColumnInfo(name = "_hour")val hour: Int,
    @ColumnInfo(name = "_minute")val minute: Int,
    @ColumnInfo(name = "_repeat")val repeatResId: Int
){

    fun getColorResId(categories: List<Category>): Int{
        var color = categories[0].colorResId
        for (item in categories){
            if(categoryTitle == item.name){
                color = item.colorResId
            }
        }
        return color
    }

    fun getFormattedTime(): String{
        return String.format(
            Locale.getDefault(), "%02d:%02d", hour, minute)
    }

    fun getFormattedDate(): String{
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(dateInMillis)
    }

    fun getRepeat(): Repeat {
        return when(repeatResId){
            R.string.everyday -> Repeat.EVERYDAY
            R.string.everyTwoDays -> Repeat.EVERYTWODAYS
            R.string.everyThreeDays -> Repeat.EVERYTHREEDAYS
            R.string.everyFourDays -> Repeat.EVERYFOURDAYS
            R.string.everyFiveDays -> Repeat.EVERYFIVEDAYS
            R.string.everySixDays -> Repeat.EVERYSIXDAYS
            R.string.everyWeek -> Repeat.EVERYWEEK
            R.string.everyMonth -> Repeat.EVERYMONTH
            R.string.everyHalfYear -> Repeat.EVERYHALFYEAR
            R.string.everyYear -> Repeat.EVERYYEAR
            else -> Repeat.NEVER
        }
    }

}