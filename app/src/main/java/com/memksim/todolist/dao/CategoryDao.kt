package com.memksim.todolist.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.memksim.todolist.objects.Category

@Dao
interface CategoryDao {

    @Insert
    fun saveNewCategory(category: Category)

    @Query("SELECT * FROM categories")
    fun getCategoriesList(): List<Category>

    @Query("SELECT * FROM categories WHERE categoryName = :categoryName")
    fun getCategory(categoryName: String): Category

    @Delete
    fun deleteCategory(category: Category)

}