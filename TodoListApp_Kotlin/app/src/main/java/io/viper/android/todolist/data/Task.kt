package io.viper.android.todolist.data

import java.util.*

/**
 * hello,android
 * @author lhyz on 2017/2/5
 *
 * 声明一个数据类
 */

data class Task(
    val taskId: String,
    val title: String?,
    val description: String?,
    val completed: Boolean
) {

    constructor(title: String?, description: String?) : this(
        UUID.randomUUID().toString(),
        title,
        description,
        false
    )

    constructor(taskId: String, title: String?, description: String?) : this(
        taskId,
        title,
        description,
        false
    )

    constructor(title: String?, description: String?, completed: Boolean) : this(
        UUID.randomUUID().toString(), title, description, completed
    )

    fun getTitleForList(): String? {
        if (title != null && !title.isEmpty()) {
            return title
        }
        return description
    }

    fun isActive(): Boolean {
        return !completed
    }

    fun isEmpty(): Boolean {
        return (title == null || title.isEmpty())
                && (description == null || description.isEmpty())
    }
}