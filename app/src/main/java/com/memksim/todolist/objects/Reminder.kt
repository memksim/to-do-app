package com.memksim.todolist.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
open class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int,
    @ColumnInfo(name = "_category") var category: String,
    @ColumnInfo(name = "_title") var title: String,
    @ColumnInfo(name = "_note") var note: String,
    @ColumnInfo(name = "_date") var date: Long,
    @ColumnInfo(name = "_hour") var hour: Int,
    @ColumnInfo(name = "_minute") var min: Int,
    @ColumnInfo(name = "_repeat") var repeat: Repeat
)

