package com.memksim.todolist.model

import android.util.Log
import com.memksim.todolist.contracts.DayServiceContract
import com.memksim.todolist.objects.Day
import java.util.*
import kotlin.collections.ArrayList

class DayService: DayServiceContract {

    private val daysList = ArrayList<Day>()

    private fun fillList(){
        val rightNow = GregorianCalendar()
        val currentDay = Day(setMonthStyle(rightNow.get(Calendar.MONTH)), setDayOfWeekStyle(rightNow.get(Calendar.DAY_OF_WEEK)), rightNow.get(Calendar.DAY_OF_MONTH), true)
        daysList.add(currentDay)
        for(i in 0..30){
            rightNow.add(Calendar.DAY_OF_MONTH, 1)
            val day = Day(
                setMonthStyle(rightNow.get(Calendar.MONTH)),
                setDayOfWeekStyle(rightNow.get(Calendar.DAY_OF_WEEK)),
                rightNow.get(Calendar.DAY_OF_MONTH),
                false
            )
            daysList.add(day)
        }
        Log.d("test", "лист заполнен")
    }

    private fun setMonthStyle(month: Int): String{
        return when(month){
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            11 -> "Dec"
            else -> "Jan"
        }
    }

    private fun setDayOfWeekStyle(day: Int): String{
        return when(day){
            1 -> "Sun"
            2 -> "Mon"
            3 -> "Tue"
            4 -> "Wed"
            5 -> "Thu"
            6 -> "Fri"
            7 -> "Sat"
            else -> "Sun"
        }
    }


    private fun clearList(){
        daysList.clear()
    }

    override fun updateList(){
        clearList()
    }

    override fun getDaysList(): List<Day>{
        fillList()
        return daysList
    }


}