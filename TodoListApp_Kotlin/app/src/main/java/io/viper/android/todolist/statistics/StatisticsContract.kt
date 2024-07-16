package io.viper.android.todolist.statistics

import io.viper.android.todolist.BasePresenter
import io.viper.android.todolist.BaseView


/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
interface StatisticsContract {
    interface View : BaseView<Presenter> {

        fun setProgressIndicator(active: Boolean)

        fun showStatistics(numberOfIncompleteTasks: Int, numberOfCompletedTasks: Int)

        fun showLoadingStatisticsError()

        fun isActive(): Boolean
    }

    interface Presenter : BasePresenter
}