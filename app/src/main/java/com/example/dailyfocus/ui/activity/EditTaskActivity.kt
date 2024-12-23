package com.example.dailyfocus.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dailyfocus.R
import com.example.dailyfocus.data.database.TaskEntity
import com.example.dailyfocus.viewmodel.TaskViewModel
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priorityEditText: EditText
    private lateinit var dueDateEditText: EditText
    private lateinit var saveButton: Button
    private var taskId: Int = -1
    private var selectedDueDate: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Find views
        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        priorityEditText = findViewById(R.id.priorityEditText)
        dueDateEditText = findViewById(R.id.dueDateEditText)
        saveButton = findViewById(R.id.saveButton)

        // Get taskId from Intent
        taskId = intent.getIntExtra("task_id", -1)

        if (taskId != -1) {
            // Observe the task details from the ViewModel
            taskViewModel.getTaskById(taskId.toLong()).observe(this, Observer { task ->
                task?.let {
                    titleEditText.setText(it.title)
                    descriptionEditText.setText(it.description)
                    priorityEditText.setText(it.priority.toString())
                    selectedDueDate = it.dueDate
                    // Format and display due date
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it.dueDate ?: 0
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH)
                    val year = calendar.get(Calendar.YEAR)
                    dueDateEditText.setText("$day/${month + 1}/$year")
                }
            })
        }

        // Handle Save button click
        saveButton.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val priority = priorityEditText.text.toString().toIntOrNull() ?: 3
        val dueDateString = dueDateEditText.text.toString()

        if (title.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
            return
        }

        // Convert due date to milliseconds if provided
        if (dueDateString.isNotEmpty()) {
            // This is a simple format: day/month/year
            val dateParts = dueDateString.split("/")
            if (dateParts.size == 3) {
                val day = dateParts[0].toIntOrNull() ?: 0
                val month = dateParts[1].toIntOrNull() ?: 0
                val year = dateParts[2].toIntOrNull() ?: 0

                val calendar = Calendar.getInstance()
                calendar.set(year, month - 1, day) // Month is 0-based in Calendar
                selectedDueDate = calendar.timeInMillis
            }
        }

        // Update the task with the new data
        val updatedTask = TaskEntity(
            id = taskId,
            title = title,
            description = description,
            priority = priority,
            dueDate = selectedDueDate
        )

        taskViewModel.updateTask(updatedTask)

        Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}
