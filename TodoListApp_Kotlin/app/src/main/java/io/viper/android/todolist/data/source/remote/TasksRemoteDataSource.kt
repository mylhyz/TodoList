package io.viper.android.todolist.data.source.remote

import android.os.Handler
import io.viper.android.todolist.data.Task
import io.viper.android.todolist.data.source.TasksDataSource
import java.util.*


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */


class TasksRemoteDataSource private constructor() : TasksDataSource {

    companion object {
        fun getInstance(): TasksRemoteDataSource {
            return TasksRemoteDataSource()
        }
    }

    val TASKS_SERVICE_DATA = LinkedHashMap<String, Task>()
    private val SERVICE_LATENCY_IN_MILLIS = 5000L

    init {
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }


    private fun addTask(title: String, description: String) {
        val newTask = Task(title, description)
        TASKS_SERVICE_DATA.put(newTask.taskId, newTask)
    }

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        // Simulate network by delaying the execution.
        val handler = Handler()
        handler.postDelayed(
            { callback.onTasksLoaded(TASKS_SERVICE_DATA.values.toList()) },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val task = TASKS_SERVICE_DATA[taskId]

        // Simulate network by delaying the execution.
        val handler = Handler()
        handler.postDelayed({ callback.onTaskLoaded(task!!) }, SERVICE_LATENCY_IN_MILLIS)
    }

    override fun saveTask(task: Task) {
        TASKS_SERVICE_DATA.put(task.taskId, task)
    }

    override fun completeTask(task: Task) {
        val completedTask = Task(task.taskId, task.title, task.description, true)
        TASKS_SERVICE_DATA.put(task.taskId, completedTask)
    }

    override fun completeTask(taskId: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(task: Task) {
        val activeTask = Task(task.taskId, task.title!!, task.description)
        TASKS_SERVICE_DATA.put(task.taskId, activeTask)
    }

    override fun activateTask(taskId: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedTasks() {
        val it = TASKS_SERVICE_DATA.entries.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (entry.value.completed) {
                it.remove()
            }
        }
    }

    override fun refreshTasks() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }
}