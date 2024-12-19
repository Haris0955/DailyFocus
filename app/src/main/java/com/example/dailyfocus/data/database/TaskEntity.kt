package com.example.dailyfocus.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val priority: Int, // 1 = High, 2 = Medium, 3 = Low
    val dueDate: Long? = null,
    val completed: Boolean = false
)
