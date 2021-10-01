package com.memksim.todolist.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.memksim.todolist.R
import com.memksim.todolist.databinding.ItemReminderBinding
import com.memksim.todolist.objects.FormattedReminder
import com.memksim.todolist.objects.Reminder
import java.text.SimpleDateFormat
import java.util.*

class RemindersAdapter(): RecyclerView.Adapter<RemindersAdapter.RemindersViewHolder>() {

    private lateinit var context: Context

    var reminders: List<FormattedReminder> = emptyList()

    class RemindersViewHolder(val binding: ItemReminderBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindersViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemReminderBinding.inflate(inflater, parent, false)

        return RemindersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindersViewHolder, position: Int) {
        val reminder = reminders[position]


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.binding.categoryColor.backgroundTintList = context.resources.getColorStateList(reminder.category.colorResId, null)
        }else{
            holder.binding.categoryColor.backgroundTintList = context.resources.getColorStateList(reminder.category.colorResId)
        }
        holder.binding.title.text = reminder.title
        holder.binding.dateTime.text = reminder.formattedDate + ", " +String.format(Locale.getDefault(), "%02d:%02d", reminder.hour, reminder.minute)
        holder.binding.whenRepeat.text = reminders[position].repeat

    }

    override fun getItemCount(): Int {
        return reminders.size
    }
}