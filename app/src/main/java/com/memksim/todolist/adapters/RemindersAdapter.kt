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

        holder.binding.title.text = reminder.title

        holder.binding.note.text = reminder.note
        if (reminder.note == ""){
            holder.binding.note.visibility = View.GONE
        }else{
            holder.binding.note.visibility = View.VISIBLE
        }

        when(reminder.priorityLevel){
            1 -> {
                holder.binding.priority3.visibility = View.VISIBLE
            }
            2 -> {
                holder.binding.priority3.visibility = View.VISIBLE
                holder.binding.priority2.visibility = View.VISIBLE
            }
            3 -> {
                holder.binding.priority3.visibility = View.VISIBLE
                holder.binding.priority2.visibility = View.VISIBLE
                holder.binding.priority1.visibility = View.VISIBLE
            }
            else -> {
                holder.binding.priority3.visibility = View.GONE
                holder.binding.priority2.visibility = View.GONE
                holder.binding.priority1.visibility = View.GONE
            }
        }

        holder.binding.whenRemind.text = reminder.getFormattedDate() + ", " + reminder.getFormattedTime()

        if (getRepeat(reminder.getRepeatAsRepeat()) != whenRepeat[0]){
            holder.binding.repeatIcon.visibility = View.VISIBLE
        }else{
            holder.binding.repeatIcon.visibility = View.INVISIBLE
        }

        val category: Category
        for (c in categories){
            if (reminder.categoryTitle == c.name){
                category = c
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                    holder.binding.categoryColor.backgroundTintList = context.resources.getColorStateList(category.colorResId, null)
                }else{
                    holder.binding.categoryColor.backgroundTintList = context.resources.getColorStateList(category.colorResId)
                }
                break
            }
        }



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