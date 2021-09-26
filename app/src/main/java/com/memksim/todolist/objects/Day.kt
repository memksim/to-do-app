package com.memksim.todolist.objects

data class Day(
    val month: String,
    val dayOfWeek: String,
    val todayDate: Int,
    var isCurrent: Boolean
)

