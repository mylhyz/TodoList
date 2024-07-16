package io.viper.android.todolist.taskdetail

import io.viper.android.todolist.BasePresenter
import io.viper.android.todolist.BaseView


/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
interface TaskDetailContract {
    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMissingTask()

        fun hideTitle()

        fun showTitle(title: String)

        fun hideDescription()

        fun showDescription(description: String)

        fun showCompletionStatus(complete: Boolean)

        fun showEditTask(taskId: String)

        fun showTaskDeleted()

        fun showTaskMarkedComplete()

        fun showTaskMarkedActive()

        fun isActive(): Boolean
    }

    interface Presenter : BasePresenter {

        fun editTask()

        fun deleteTask()

        fun completeTask()

        fun activateTask()
    }
}