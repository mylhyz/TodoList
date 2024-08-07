/*
 * Copyright (c) 2016 lhyz Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.viper.android.todolist.todolist.data.source.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import io.viper.android.todolist.todolist.data.Task;
import io.viper.android.todolist.todolist.data.source.TasksDataSource;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
public class TasksLocalDataSource implements TasksDataSource {

    private static TasksLocalDataSource INSTANCE;

    Dao<Task, String> mTasksDao;

    private TasksLocalDataSource(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        try {
            mTasksDao = databaseHelper.getTasksDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TasksLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context);
        }
        return INSTANCE;
    }

    private @Nullable Task findTaskById(String taskId) throws SQLException {
        QueryBuilder<Task, String> builder = mTasksDao.queryBuilder();
        builder.where().eq("taskId", taskId);
        PreparedQuery<Task> preparedQuery = builder.prepare();
        return mTasksDao.queryForFirst(preparedQuery);
    }

    @Override
    public void getAllTasks(@NonNull LoadTasksCallback callback) {
        try {
            List<Task> results = mTasksDao.queryForAll();
            callback.onTasksLoaded(results);
        } catch (SQLException e) {
            callback.onDataNotAvailable();
            e.printStackTrace();
        }
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        try {
            Task task = findTaskById(taskId);
            if (task == null) {
                callback.onDataNotAvailable();
                return;
            }
            callback.onTaskLoaded(task);
        } catch (SQLException e) {
            callback.onDataNotAvailable();
            e.printStackTrace();
        }
    }

    @Override
    public void saveTask(@NonNull Task task) {
        try {
            mTasksDao.create(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(@NonNull Task task) {
        try {
            mTasksDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        try {
            Task task = findTaskById(taskId);
            if (task == null) {
                return;
            }
            task.setCompleted(true);
            mTasksDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        try {
            Task task = findTaskById(taskId);
            if (task == null) {
                return;
            }
            task.setCompleted(false);
            mTasksDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearCompletedTasks() {
        try {
            DeleteBuilder<Task, String> builder = mTasksDao.deleteBuilder();
            builder.where().eq("completed", true);
            PreparedDelete<Task> preparedDelete = builder.prepare();
            mTasksDao.delete(preparedDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshTasks() {
        try {
            List<Task> tasks = mTasksDao.queryForAll();
            for (Task task : tasks) {
                mTasksDao.refresh(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllTasks() {
        try {
            List<Task> tasks = mTasksDao.queryForAll();
            mTasksDao.delete(tasks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        try {
            DeleteBuilder<Task, String> builder = mTasksDao.deleteBuilder();
            builder.where().eq("taskId", taskId);
            PreparedDelete<Task> preparedDelete = builder.prepare();
            mTasksDao.delete(preparedDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
