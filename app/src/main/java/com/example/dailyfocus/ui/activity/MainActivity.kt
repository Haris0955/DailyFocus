package com.example.dailyfocus.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyfocus.R
import com.example.dailyfocus.ui.adapter.TaskAdapter
import com.example.dailyfocus.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with an empty list
        taskAdapter = TaskAdapter(emptyList()) { task ->
            // Handle task click (e.g., navigate to task detail)
            Toast.makeText(this, "Clicked on ${task.title}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = taskAdapter

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Observe the task list from the ViewModel
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            taskAdapter.updateTasks(tasks)
        })

        // Initialize the Add Task button
        val addTaskButton: Button = findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}
