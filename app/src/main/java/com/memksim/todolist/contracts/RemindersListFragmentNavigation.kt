package com.memksim.todolist.contracts

import com.memksim.todolist.objects.Reminder

interface RemindersListFragmentNavigation {

    fun openAddReminderFragment()

    fun openReminderInfoFragment(id: Int)
}