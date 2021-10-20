package com.memksim.todolist.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.memksim.todolist.R
import com.memksim.todolist.databinding.ItemDayBinding
import com.memksim.todolist.objects.Day

class DaysAdapter: RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    lateinit var context: Context

    var days: List<Day> = emptyList()

    class DaysViewHolder(
        val binding: ItemDayBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemDayBinding.inflate(inflater, parent, false)

        return DaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        if (days[position].isCurrent){
            holder.binding.currentDayCircle.background = AppCompatResources.getDrawable(context, R.drawable.item_current_day_background)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.binding.dayOfMonth.setTextColor(context.resources.getColor(R.color.white, null))
            }else{
                holder.binding.dayOfMonth.setTextColor(context.resources.getColor(R.color.white))
            }
        }else{
            holder.binding.currentDayCircle.background = AppCompatResources.getDrawable(context, R.drawable.item_day_background)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.binding.dayOfMonth.setTextColor(context.resources.getColor(R.color.black, null))
            }else{
                holder.binding.dayOfMonth.setTextColor(context.resources.getColor(R.color.black))
            }
        }

        holder.binding.dayOfWeek.text = days[position].dayOfWeek
        holder.binding.dayOfMonth.text = days[position].todayDate.toString()

    }

    override fun getItemCount(): Int {
        return days.size
    }

}