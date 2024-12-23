package com.example.dailyfocus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyfocus.R
import com.example.dailyfocus.data.database.TaskEntity

class TaskAdapter(
    private var tasks: List<TaskEntity>,
    private val onTaskClick: (TaskEntity) -> Unit,
    private val onDeleteClick: (TaskEntity) -> Unit,// Add the delete callback
    private val onEditClick: (TaskEntity) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, onTaskClick, onDeleteClick,onEditClick)
    }

    override fun getItemCount(): Int = tasks.size

    // Update tasks dynamically
    fun updateTasks(newTasks: List<TaskEntity>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    // ViewHolder class to hold the view references
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.taskDescription)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        private val editButton: TextView = itemView.findViewById(R.id.editText) // Add Edit Button


        fun bind(
            task: TaskEntity,
            onTaskClick: (TaskEntity) -> Unit,
            onDeleteClick: (TaskEntity) -> Unit,
            onEditClick: (TaskEntity) -> Unit

        ) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description ?: "No description"
            // Set click listener for the task
            itemView.setOnClickListener { onTaskClick(task) }
            deleteButton.setOnClickListener {
                onDeleteClick(task)
            }
            editButton.setOnClickListener { onEditClick(task) }

        }
    }
}
