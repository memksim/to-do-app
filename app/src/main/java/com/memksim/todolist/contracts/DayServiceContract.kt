package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Day

interface DayServiceContract {

    fun getDaysList(): List<Day>

    fun updateList()

}