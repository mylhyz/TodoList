package com.lhyz.android.todolist.data.source.local

/**
 * hello,android
 * @author lhyz on 2017/2/5
 */
object TasksPersistenceContract {
    object TaskEntry {
        const val ID = "_id"
        const val TABLE_NAME = "tasks"
        const val COLUMN_NAME_TASK_ID = "taskId"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_COMPLETED = "completed"
    }
}