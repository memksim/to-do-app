package com.memksim.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.memksim.todolist.databinding.MainLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}