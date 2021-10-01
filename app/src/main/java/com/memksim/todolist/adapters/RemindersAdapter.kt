package com.memksim.todolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memksim.todolist.R
import com.memksim.todolist.databinding.ItemReminderBinding
import com.memksim.todolist.objects.Reminder
import java.text.SimpleDateFormat
import java.util.*

class RemindersAdapter(): RecyclerView.Adapter<RemindersAdapter.RemindersViewHolder>() {

    private lateinit var context: Context

    var reminders: List<Reminder> = emptyList()

    class RemindersViewHolder(val binding: ItemReminderBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindersViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemReminderBinding.inflate(inflater, parent, false)

        return RemindersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindersViewHolder, position: Int) {
        val format = SimpleDateFormat("dd.MM.yyyy")

        holder.binding.categoryColor.setImageResource(getColor(reminders[position].category))
        holder.binding.title.text = reminders[position].title
        holder.binding.dateTime.text = format.format(reminders[position].date) + ", " +
                String.format(Locale.getDefault(), "%02d:%02d", reminders[position].hour, reminders[position].min)
        holder.binding.whenRepeat.text = reminders[position].repeat
    }

    private fun getColor(categoryTitle: String): Int{
        return when(categoryTitle){
            else -> {
                R.color.second_color
            }
        }
    }

    override fun getItemCount(): Int {
        return reminders.size
    }
}