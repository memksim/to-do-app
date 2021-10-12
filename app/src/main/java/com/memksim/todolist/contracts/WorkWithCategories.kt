package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Category

interface WorkWithCategories {

    fun getCategoriesList(): List<Category>

    fun getCategoriesNames(): List<String>

}