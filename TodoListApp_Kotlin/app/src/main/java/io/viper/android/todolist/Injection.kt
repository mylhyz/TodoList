package io.viper.android.todolist

import android.content.Context
import io.viper.android.todolist.data.source.TasksRepository
import io.viper.android.todolist.data.source.local.TasksLocalDataSource
import io.viper.android.todolist.data.source.remote.TasksRemoteDataSource

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class Injection {
    companion object {
        fun provideTasksRepository(context: Context): TasksRepository {
            return TasksRepository.getInstance(
                    TasksRemoteDataSource.getInstance(),
                    TasksLocalDataSource.getInstance(context)
            )
        }
    }
}