package io.viper.android.todolist.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * hello,android
 * @author lhyz on 2017/2/5
 */


class TasksDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1

        const val DATABASE_NAME = "Tasks.db"

        private const val TEXT_TYPE = " TEXT"

        private const val BOOLEAN_TYPE = " INTEGER"

        private const val COMMA_SEP = ","

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TasksPersistenceContract.TaskEntry.TABLE_NAME + " (" + TasksPersistenceContract.TaskEntry.ID + TEXT_TYPE + " PRIMARY KEY," + TasksPersistenceContract.TaskEntry.COLUMN_NAME_TASK_ID + TEXT_TYPE + COMMA_SEP + TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP + TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP + TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED + BOOLEAN_TYPE + " )"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TasksPersistenceContract.TaskEntry.TABLE_NAME
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}