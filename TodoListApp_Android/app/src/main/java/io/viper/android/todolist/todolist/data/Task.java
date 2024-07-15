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
package io.viper.android.todolist.todolist.data;

import androidx.annotation.Nullable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
@DatabaseTable(tableName = "tasks")
public final class Task implements Serializable {

    private static final long serialVersionUID = -7050492019251241384L;

    @DatabaseField(id = true)
    private String id;

    @DatabaseField(canBeNull = false)
    private String taskId;

    @Nullable
    @DatabaseField(canBeNull = false)
    private String title;

    @Nullable
    @DatabaseField(canBeNull = false)
    private String description;

    @DatabaseField
    private boolean completed;

    public Task() {
        //For OrmLite
    }

    /**
     * Use this constructor to create a new active Task.
     */
    public Task(@Nullable String title, @Nullable String description) {
        this.taskId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     */
    public Task(@Nullable String title, @Nullable String description, String taskId) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    /**
     * Use this constructor to create a new completed Task.
     */
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        this.taskId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     */
    public Task(@Nullable String title, @Nullable String description, String taskId, boolean completed) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getTitleForList() {
        if (title != null && !title.equals("")) {
            return title;
        } else {
            return description;
        }
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isActive() {
        return !completed;
    }

    public boolean isEmpty() {
        return (title == null || "".equals(title)) &&
                (description == null || "".equals(description));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;
        return completed == task.completed && taskId.equals(task.taskId) && title.equals(task.title) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        int result = taskId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Boolean.hashCode(completed);
        return result;
    }

    @Override
    public String toString() {
        return "Task with title " + title;
    }

}
