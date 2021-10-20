package com.memksim.todolist.objects

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.memksim.todolist.R
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*


@Parcelize
@Entity(tableName = "reminders")
open class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int,
    @ColumnInfo(name = "_title")var title: String,
    @ColumnInfo(name = "_note")var note: String,
    @ColumnInfo(name = "_date")var dateInMillis: Long,
    @ColumnInfo(name = "_hour")var hour: Int,
    @ColumnInfo(name = "_minute")var minute: Int,
    @ColumnInfo(name = "_repeat")var repeatResId: Int,
    @ColumnInfo(name = "_category")var categoryTitle: String,
    @ColumnInfo(name = "_priority") var priorityLevel: Int
): Parcelable {

    fun getPriorityResId(): Int{
        return when(priorityLevel){
            1 -> R.string.level1
            2 -> R.string.level2
            3 -> R.string.level3
            else -> R.string.defaultLevel
        }
    }

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

    fun getRepeatAsRepeat(): Repeat {
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