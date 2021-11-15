package com.memksim.todolist.objects

data class Day(
    val month: String,
    val dayOfWeek: String,
    val dateInMillis: Int,
    var isCurrent: Boolean
)

