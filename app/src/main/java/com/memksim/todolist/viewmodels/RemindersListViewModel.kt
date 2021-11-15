package com.memksim.todolist.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memksim.todolist.R
import com.memksim.todolist.contracts.RemindersListViewModelContract
import com.memksim.todolist.contracts.Sortable
import com.memksim.todolist.model.Repository
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder

class RemindersListViewModel(
    application: Application
): AndroidViewModel(application), RemindersListViewModelContract, Sortable {

    private val repository = Repository(application)

    private val remindersMutableLiveData: MutableLiveData<List<Reminder>> by lazy {
        MutableLiveData<List<Reminder>>()
    }
    var remindersLiveData: LiveData<List<Reminder>> = remindersMutableLiveData

    private fun loadList(){
        remindersMutableLiveData.value = repository.getReminders()
    }

    override fun updateList() {
        loadList()
    }

    override fun getCategories(): List<Category> {
        return repository.getCategories()
    }

    override fun addReminder(reminder: Reminder) {
        repository.addReminder(reminder)
        loadList()
    }

    override fun updateReminder(reminder: Reminder) {
        repository.updateReminder(reminder)
        loadList()
    }

    override fun deleteReminder(id: Int) {
        val reminders = remindersLiveData.value
        repository.deleteReminder(reminders!![id])
        loadList()
    }

    override fun getByDate(dateInMillis: Long){
        val sortedList = ArrayList<Reminder>()

        val reminders: List<Reminder>? = remindersLiveData.value
        if (reminders != null) {
            for (i in reminders){
                if(i.dateInMillis == dateInMillis){
                    sortedList.add(i)
                }
            }
        }

        remindersMutableLiveData.value = sortedList
    }

    override fun getByCategory(categoryTitle: String){
        val sortedList = ArrayList<Reminder>()

        val reminders: List<Reminder>? = remindersLiveData.value
        if (reminders != null) {
            for (i in reminders){
                if(i.categoryTitle == categoryTitle){
                    sortedList.add(i)
                }
            }
        }

        remindersMutableLiveData.value = sortedList
    }

    override fun sortByNearest(){
        //TODO("Not yet implemented")
    }

    override fun sortByHighPriority(){
        val sortedList = ArrayList<Reminder>()

        val defaultPriority = ArrayList<Reminder>()
        val level1 = ArrayList<Reminder>()
        val level2 = ArrayList<Reminder>()
        val level3 = ArrayList<Reminder>()

        val reminders: List<Reminder>? = remindersLiveData.value
        if (reminders != null) {
            for (i in reminders){

                when(i.repeatResId){
                    R.string.level1 ->{
                        level1.add(i)
                    }
                    R.string.level2 ->{
                        level2.add(i)
                    }
                    R.string.level3 ->{
                        level3.add(i)
                    }
                    else ->{
                        defaultPriority.add(i)
                    }
                }

            }
        }

        sortedList.addAll(level3)
        sortedList.addAll(level2)
        sortedList.addAll(level1)
        sortedList.addAll(defaultPriority)
        remindersMutableLiveData.value = sortedList
    }

    override fun sortByLowPriority() {
        val sortedList = ArrayList<Reminder>()

        val defaultPriority = ArrayList<Reminder>()
        val level1 = ArrayList<Reminder>()
        val level2 = ArrayList<Reminder>()
        val level3 = ArrayList<Reminder>()

        val reminders: List<Reminder>? = remindersLiveData.value
        if (reminders != null) {
            for (i in reminders){

                when(i.repeatResId){
                    R.string.level1 ->{
                        level1.add(i)
                    }
                    R.string.level2 ->{
                        level2.add(i)
                    }
                    R.string.level3 ->{
                        level3.add(i)
                    }
                    else ->{
                        defaultPriority.add(i)
                    }
                }

            }
        }

        sortedList.addAll(defaultPriority)
        sortedList.addAll(level1)
        sortedList.addAll(level2)
        sortedList.addAll(level3)
        remindersMutableLiveData.value = sortedList
    }

    override fun sortByAlphabet(){
        //TODO("Not yet implemented")
    }

    override fun sortFromOldest(){
        //TODO("Not yet implemented")
    }

    override fun sortFromNewest(){
        //TODO("Not yet implemented")
    }


}