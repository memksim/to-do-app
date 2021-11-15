package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Reminder

interface Sortable {

    fun getByDate(dateInMillis: Long)

    fun getByCategory(categoryTitle: String)

    fun sortByNearest()

    fun sortByHighPriority()

    fun sortByLowPriority()

    fun sortByAlphabet()

    fun sortFromOldest()

    fun sortFromNewest()

}