package com.memksim.todolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memksim.todolist.R
import com.memksim.todolist.contracts.CategoryIconContract
import com.memksim.todolist.databinding.ItemIconAssetBinding

class IconAssetAdapter(
    listener: CategoryIconContract
): RecyclerView.Adapter<IconAssetAdapter.IconAssetViewHolder>() {
    lateinit var context: Context

    private val listOfAssets = listOf<Int>(
        R.drawable.ic_category_list,
        R.drawable.ic_category_work,
        R.drawable.ic_baseline_notifications_24,
        R.drawable.ic_category_attach_money,
        R.drawable.ic_baseline_credit_card,
        R.drawable.ic_baseline_shopping_cart,
        R.drawable.ic_category_brush,
        R.drawable.ic_category_favorite,
        R.drawable.ic_category_home,
        R.drawable.ic_baseline_pets_24,
        R.drawable.ic_baseline_cake,
        R.drawable.ic_baseline_audiotrack,
        R.drawable.ic_baseline_directions_run,
        R.drawable.ic_baseline_directions_bike,
        R.drawable.ic_baseline_directions_car,
        R.drawable.ic_baseline_directions_bus,
        R.drawable.ic_baseline_tablet_android,
        R.drawable.ic_baseline_sports_basketball,
        R.drawable.ic_baseline_sports_soccer,
        R.drawable.ic_baseline_sports_volleyball,
        R.drawable.ic_baseline_sports,
        R.drawable.ic_baseline_sports_esports,
        R.drawable.ic_baseline_healing,
        R.drawable.ic_baseline_grass,
    )

    class IconAssetViewHolder(
        val binding: ItemIconAssetBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconAssetAdapter.IconAssetViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemIconAssetBinding.inflate(inflater, parent, false)

        return IconAssetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconAssetViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listOfAssets.size
    }
}