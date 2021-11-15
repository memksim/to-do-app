
package com.memksim.todolist.fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.memksim.todolist.R
import com.memksim.todolist.adapters.ActionListener
import com.memksim.todolist.adapters.DaysAdapter
import com.memksim.todolist.adapters.RemindersAdapter
import com.memksim.todolist.contracts.RemindersListFragmentNavigation
import com.memksim.todolist.databinding.FragmentRemindersListBinding
import com.memksim.todolist.objects.Category
import com.memksim.todolist.viewmodels.DaysListViewModel
import com.memksim.todolist.viewmodels.RemindersListViewModel


class RemindersListFragment: Fragment(R.layout.fragment_reminders_list), RemindersListFragmentNavigation,
    ActionListener {

    private var _binding: FragmentRemindersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var daysListViewModel: DaysListViewModel
    private lateinit var remindersListViewModel: RemindersListViewModel

    private lateinit var navController: NavController

    private lateinit var daysAdapter: DaysAdapter
    private lateinit var remindersAdapter: RemindersAdapter

    private var categories: List<Category> = emptyList()

    private var daysListHidden = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRemindersListBinding.bind(view)
        setHasOptionsMenu(true)

        daysListViewModel = ViewModelProvider(this)[DaysListViewModel::class.java]
        remindersListViewModel = ViewModelProvider(this)[RemindersListViewModel::class.java]

        categories = remindersListViewModel.getCategories()

        val horizontalLayoutManager = LinearLayoutManager(requireContext())
        horizontalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        daysAdapter = DaysAdapter()
        daysListViewModel.days.observe(viewLifecycleOwner, Observer {
            daysAdapter.notifyDataSetChanged()
            daysAdapter.days = it
            binding.toolBar.title = setMonthStyle(it[0].month)
        })
        binding.daysRecyclerView.layoutManager = horizontalLayoutManager
        binding.daysRecyclerView.adapter = daysAdapter

        val callback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT ->{
                        remindersListViewModel.deleteReminder(viewHolder.adapterPosition)
                        Toast.makeText(requireContext(), R.string.deleted, Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT ->{
                        Toast.makeText(requireContext(), R.string.completed, Toast.LENGTH_SHORT).show()
                    }
                }

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val swipeBackground: Drawable
                val itemView = viewHolder.itemView
                var icon = requireContext().resources.getDrawable(R.drawable.ic_delete, null)
                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                if (dX < 0){
                    swipeBackground = requireContext()
                        .resources
                        .getDrawable(R.drawable.swipe_delete_reminder_background, null)
                    swipeBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom)

                    icon = requireContext()
                        .resources
                        .getDrawable(R.drawable.ic_delete, null)
                    icon.setBounds(
                        itemView.right - iconMargin - icon.intrinsicWidth,
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.bottom - iconMargin
                    )
                }else{
                    swipeBackground = requireContext()
                        .resources
                        .getDrawable(R.drawable.swipe_done_reminder_background, null)
                    swipeBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom)

                    icon = requireContext()
                        .resources
                        .getDrawable(R.drawable.ic_done, null)
                    icon.setBounds(
                        itemView.left + iconMargin,
                        itemView.top + iconMargin,
                        itemView.left + iconMargin + icon.intrinsicWidth,
                        itemView.bottom - iconMargin)
                }

                swipeBackground.draw(c)
                icon.draw(c)

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )


            }

        }

        remindersAdapter = RemindersAdapter(this)
        ItemTouchHelper(callback).attachToRecyclerView(binding.remindersRecyclerView)
        remindersAdapter.categories = remindersListViewModel.getCategories()
        remindersListViewModel.remindersLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("test", "remindersListViewModel.remindersLiveData")
            remindersAdapter.notifyDataSetChanged()
            remindersAdapter.reminders = it
        })
        binding.remindersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.remindersRecyclerView.adapter = remindersAdapter

        binding.newReminder.setOnClickListener {
            openAddReminderFragment()
        }

        navController = findNavController()

        if(categories.size == 1){
            binding.navView.menu.add(R.id.categoriesGroup, Menu.NONE, 1, R.string.newCategory).setIcon(R.drawable.ic_baseline_add_24)
        }else{
            for(i in 1 until categories.size){
                binding.navView.menu.add(R.id.categoriesGroup, i, 1, categories[i].name).setIcon(categories[i].iconResId)
            }
        }



        val drawerLayout = getView()?.findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.toolBar.setNavigationOnClickListener {
            drawerLayout?.openDrawer(GravityCompat.START, true)
        }


        binding.toolBar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.show -> {
                    if(daysListHidden){
                        binding.daysRecyclerView.visibility = View.VISIBLE
                        //binding.toolBar.show.setImageResource(R.drawable.ic_arrow_up)
                        menuItem.setIcon(R.drawable.ic_arrow_up)
                        daysListHidden = false
                    }else{
                        binding.daysRecyclerView.visibility = View.GONE
                        //binding.arrow.setImageResource(R.drawable.ic_arrow_down)
                        menuItem.setIcon(R.drawable.ic_arrow_down)
                        daysListHidden = true
                    }
                    true
                }
                R.id.nearest -> {
                    //todo
                    true
                }
                R.id.highPriority ->{
                    //todo
                    true
                }
                R.id.lowPriority -> {
                    //todo
                    true
                }
                R.id.alphabet -> {
                    //todo
                    true
                }
                R.id.newest -> {
                    //todo
                    true
                }
                R.id.oldest -> {
                    //todo
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        daysListViewModel.updateDays()
        remindersListViewModel.updateList()
    }

    override fun openAddReminderFragment() {
        navController.navigate(R.id.action_remindersListFragment_to_addReminderFragment)
    }

    override fun openReminderInfoFragment(id: Int) {
        val action = RemindersListFragmentDirections.actionRemindersListFragmentToReminderInfoFragment(id = id)
        navController.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setMonthStyle(month: String):String{
        return when(month){
            "Jan" -> getString(R.string.Jan)
            "Feb" -> getString(R.string.Feb)
            "Mar" -> getString(R.string.Mar)
            "Apr" -> getString(R.string.Apr)
            "May" -> getString(R.string.May)
            "Jun" -> getString(R.string.Jun)
            "Jul" -> getString(R.string.Jul)
            "Aug" -> getString(R.string.Aug)
            "Sep" -> getString(R.string.Sep)
            "Oct" -> getString(R.string.Oct)
            "Nov" -> getString(R.string.Nov)
            "Dec" -> getString(R.string.Dec)
            else -> ""
        }
    }

    override fun onClickedReminder(id: Int) {
        openReminderInfoFragment(id = id)
    }


}