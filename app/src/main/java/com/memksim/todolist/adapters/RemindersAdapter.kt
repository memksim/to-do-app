package com.memksim.todolist.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memksim.todolist.R
import com.memksim.todolist.databinding.ItemReminderBinding
import com.memksim.todolist.objects.Category
import com.memksim.todolist.objects.Reminder
import com.memksim.todolist.objects.Repeat
import com.memksim.todolist.objects.Repeat.*

interface ActionListener{

    fun onClickedReminder(id: Int)

}

class RemindersAdapter(
    private val listener: ActionListener
): RecyclerView.Adapter<RemindersAdapter.RemindersViewHolder>() {

    private lateinit var context: Context

    var reminders: List<Reminder> = emptyList()
    var categories: List<Category> = emptyList()

    private var whenRepeat: List<String> = emptyList()
    private var id = 0

    class RemindersViewHolder(val binding: ItemReminderBinding, private val listener: ActionListener):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener.onClickedReminder(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindersViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemReminderBinding.inflate(inflater, parent, false)

        whenRepeat = listOf(
            context.getString(R.string.never),
            context.getString(R.string.everyday),
            context.getString(R.string.everyTwoDays),
            context.getString(R.string.everyThreeDays),
            context.getString(R.string.everyFourDays),
            context.getString(R.string.everyFiveDays),
            context.getString(R.string.everySixDays),
            context.getString(R.string.everyWeek),
            context.getString(R.string.everyMonth),
            context.getString(R.string.everyHalfYear),
            context.getString(R.string.everyYear)
        )

        val viewHolder = RemindersViewHolder(binding, listener)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RemindersViewHolder, position: Int) {
        val reminder = reminders[position]
        id = position

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.binding.categoryColor.backgroundTintList = context.resources.getColorStateList(reminder.getColorResId(categories), null)
        }else{
            holder.binding.categoryColor.backgroundTintList = context.resources.getColorStateList(reminder.getColorResId(categories))
        }
        holder.binding.title.text = reminder.title
        holder.binding.dateTime.text = reminder.getFormattedDate() + ", " + reminder.getFormattedTime()
        holder.binding.whenRepeat.text = getRepeat(reminder.getRepeat())

    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    private fun getRepeat(r: Repeat): String{
        return when(r){
            EVERYDAY -> whenRepeat[1]
            EVERYTWODAYS -> whenRepeat[2]
            EVERYTHREEDAYS -> whenRepeat[3]
            EVERYFOURDAYS -> whenRepeat[4]
            EVERYFIVEDAYS -> whenRepeat[5]
            EVERYSIXDAYS -> whenRepeat[6]
            EVERYWEEK -> whenRepeat[7]
            EVERYMONTH -> whenRepeat[8]
            EVERYHALFYEAR -> whenRepeat[9]
            EVERYYEAR -> whenRepeat[10]
            else -> whenRepeat[0]
        }
    }

    private fun getReminder(reminderId: Int): Reminder{
        return reminders[reminderId]
    }
}