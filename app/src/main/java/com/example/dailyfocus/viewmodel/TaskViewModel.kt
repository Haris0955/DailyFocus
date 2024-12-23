package com.example.dailyfocus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.dailyfocus.data.database.AppDatabase
import com.example.dailyfocus.data.database.TaskEntity

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = AppDatabase.getDatabase(application).taskDao()
    val allTasks: LiveData<List<TaskEntity>> = taskDao.getAllTasks()

    // Insert a new task
    fun insertTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }

    // Update an existing task
    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    // Delete a task by its ID
    fun deleteTaskById(taskId: Int) {
        viewModelScope.launch {
            taskDao.deleteTaskById(taskId)
        }
    }

    // Retrieve a task by its ID
    fun getTaskById(taskId: Long): LiveData<TaskEntity> {
        return taskDao.getTaskById(taskId)
    }
}
