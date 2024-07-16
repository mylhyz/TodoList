package com.lhyz.android.todolist.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import com.lhyz.android.todolist.data.Task
import com.lhyz.android.todolist.data.source.TasksDataSource
import com.lhyz.android.todolist.data.source.local.TasksPersistenceContract.TaskEntry
import java.util.*

/**
 * hello,android
 * @author lhyz on 2017/2/5
 */
class TasksLocalDataSource private constructor(context: Context) : TasksDataSource {

    companion object {
        fun getInstance(context: Context): TasksLocalDataSource {
            return TasksLocalDataSource(context)
        }
    }

    private val mDbHelper: TasksDbHelper = TasksDbHelper(context)

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        val tasks = ArrayList<Task>()
        val db = mDbHelper.writableDatabase
        val projection = arrayOf(
            TaskEntry.COLUMN_NAME_TASK_ID,
            TaskEntry.COLUMN_NAME_TITLE,
            TaskEntry.COLUMN_NAME_DESCRIPTION,
            TaskEntry.COLUMN_NAME_COMPLETED
        )
        val c = db.query(TaskEntry.TABLE_NAME, projection, null, null, null, null, null)
        if (c != null && c.count > 0) {
            while (c.moveToNext()) {
                val itemId = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TASK_ID))
                val title = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TITLE))
                val description =
                    c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_DESCRIPTION))
                val completed =
                    c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_COMPLETED)) == 1
                val task = Task(itemId, title, description, completed)
                tasks.add(task)
            }
        }
        c?.close()
        db.close()

        if (tasks.isEmpty()) {
            callback.onDataNotAvailable()
        } else {
            callback.onTasksLoaded(tasks)
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val db = mDbHelper.readableDatabase

        val projection = arrayOf(
            TaskEntry.COLUMN_NAME_TASK_ID,
            TaskEntry.COLUMN_NAME_TITLE,
            TaskEntry.COLUMN_NAME_DESCRIPTION,
            TaskEntry.COLUMN_NAME_COMPLETED
        )

        val selection = TaskEntry.COLUMN_NAME_TASK_ID + " LIKE ?"
        val selectionArgs = arrayOf(taskId)

        val c = db.query(
            TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null
        )

        var task: Task? = null

        if (c != null && c.count > 0) {
            c.moveToFirst()
            val itemId = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TASK_ID))
            val title = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TITLE))
            val description =
                c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_DESCRIPTION))
            val completed = c.getInt(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_COMPLETED)) == 1
            task = Task(itemId, title, description, completed)
        }
        c?.close()

        db.close()

        if (task != null) {
            callback.onTaskLoaded(task)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun saveTask(task: Task) {
        val db = mDbHelper.writableDatabase

        val values = ContentValues()
        values.put(TaskEntry.COLUMN_NAME_TASK_ID, task.taskId)
        values.put(TaskEntry.COLUMN_NAME_TITLE, task.title)
        values.put(TaskEntry.COLUMN_NAME_DESCRIPTION, task.description)
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, task.completed)

        db.insert(TaskEntry.TABLE_NAME, null, values)

        db.close()
    }

    override fun completeTask(task: Task) {
        val db = mDbHelper.writableDatabase

        val values = ContentValues()
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, true)

        val selection = TaskEntry.COLUMN_NAME_TASK_ID + " LIKE ?"
        val selectionArgs = arrayOf(task.taskId)

        db.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs)

        db.close()
    }

    override fun completeTask(taskId: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(task: Task) {
        val db = mDbHelper.writableDatabase

        val values = ContentValues()
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, false)

        val selection = TaskEntry.COLUMN_NAME_TASK_ID + " LIKE ?"
        val selectionArgs = arrayOf(task.taskId)

        db.update(TaskEntry.TABLE_NAME, values, selection, selectionArgs)

        db.close()
    }

    override fun activateTask(taskId: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedTasks() {
        val db = mDbHelper.writableDatabase

        val selection = TaskEntry.COLUMN_NAME_COMPLETED + " LIKE ?"
        val selectionArgs = arrayOf("1")

        db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs)

        db.close()
    }

    override fun refreshTasks() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllTasks() {
        val db = mDbHelper.writableDatabase
        try {
            db.delete(TaskEntry.TABLE_NAME, null, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        db.close()
    }

    override fun deleteTask(taskId: String) {
        val db = mDbHelper.writableDatabase

        val selection = TaskEntry.COLUMN_NAME_TASK_ID + " LIKE ?"
        val selectionArgs = arrayOf(taskId)

        db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs)

        db.close()
    }
}