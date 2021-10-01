package com.memksim.todolist.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey @ColumnInfo(name = "categoryName") val name: String,
    @ColumnInfo(name = "colorResId") val colorResId: Int
)