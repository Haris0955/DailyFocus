package com.example.dailyfocus.data.model

data class Task(
    val id: Int,
    val title: String,
    val description: String?,
    val dueDate: Long?,
    val completed: Boolean
)
