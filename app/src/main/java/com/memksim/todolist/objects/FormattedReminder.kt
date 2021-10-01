package com.memksim.todolist.objects

data class FormattedReminder(
    val title: String,
    val note: String,
    val category: Category,
    val dateInMillis: Long,
    val formattedDate: String,
    val hour: Int,
    val minute: Int,
    val repeat: String
)