package com.example.dailyfocus.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dailyfocus.R
import com.example.dailyfocus.data.database.TaskEntity
import com.example.dailyfocus.viewmodel.TaskViewModel
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priorityEditText: EditText
    private lateinit var dueDateButton: Button
    private lateinit var saveButton: Button
    private var selectedDueDate: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Find views
        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        priorityEditText = findViewById(R.id.priorityEditText)
        dueDateButton = findViewById(R.id.dueDateButton)
        saveButton = findViewById(R.id.saveButton)

        // Handle due date selection
        dueDateButton.setOnClickListener {
            showDatePicker()
        }

        // Handle save task
        saveButton.setOnClickListener {
            saveTask()
        }
    }

    // Method to show the DatePickerDialog
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            selectedDueDate = selectedCalendar.timeInMillis
            dueDateButton.text = "Due Date: ${selectedDay}/${selectedMonth + 1}/${selectedYear}"
        }, year, month, day)

        datePicker.show()
    }

    // Method to save the task
    private fun saveTask() {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val priority = priorityEditText.text.toString().toIntOrNull() ?: 3

        if (title.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the TaskEntity object
        val newTask = TaskEntity(
            title = title,
            description = description,
            priority = priority,
            dueDate = selectedDueDate
        )

        // Insert the task into the database via ViewModel
        taskViewModel.insertTask(newTask)

        // Show a success message and close the activity
        Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}
