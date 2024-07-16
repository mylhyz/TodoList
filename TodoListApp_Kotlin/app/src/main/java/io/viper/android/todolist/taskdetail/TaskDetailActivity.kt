package io.viper.android.todolist.taskdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.viper.android.todolist.Injection
import io.viper.android.todolist.R
import io.viper.android.todolist.util.ActivityUtils


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class TaskDetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_TASK_ID = "TASK_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.taskdetail_act)

        // Set up the toolbar.
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        ab.setDisplayShowHomeEnabled(true)

        // Get the requested task id
        val taskId = intent.getStringExtra(EXTRA_TASK_ID)

        var taskDetailFragment: TaskDetailFragment? = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as TaskDetailFragment?

        if (taskDetailFragment == null) {
            taskDetailFragment = TaskDetailFragment.newInstance(taskId)

            ActivityUtils.addFragmentToActivity(supportFragmentManager,
                    taskDetailFragment, R.id.contentFrame)
        }

        // Create the presenter
        TaskDetailPresenter(
                taskId,
                Injection.provideTasksRepository(applicationContext),
                taskDetailFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}