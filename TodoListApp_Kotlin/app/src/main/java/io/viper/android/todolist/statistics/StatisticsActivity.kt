package io.viper.android.todolist.statistics

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import io.viper.android.todolist.Injection
import io.viper.android.todolist.R
import io.viper.android.todolist.tasks.TasksActivity
import io.viper.android.todolist.util.ActivityUtils


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class StatisticsActivity : AppCompatActivity() {
    private var mDrawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics_act)

        // Set up the toolbar.
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setTitle(R.string.statistics_title)
        ab.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab.setDisplayHomeAsUpEnabled(true)

        // Set up the navigation drawer.
        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        mDrawerLayout!!.setStatusBarBackground(R.color.colorPrimaryDark)
        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        if (navigationView != null) {
            setupDrawerContent(navigationView)
        }

        var statisticsFragment: StatisticsFragment? = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as StatisticsFragment?
        if (statisticsFragment == null) {
            statisticsFragment = StatisticsFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager,
                    statisticsFragment, R.id.contentFrame)
        }

        StatisticsPresenter(
                Injection.provideTasksRepository(applicationContext), statisticsFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.list_navigation_menu_item -> {
                    val intent = Intent(this@StatisticsActivity, TasksActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                R.id.statistics_navigation_menu_item -> {
                    // Do nothing, we're already on that screen
                }
                else -> {
                }
            }
            // Close the navigation drawer when an item is selected.
            item.isChecked = true
            mDrawerLayout!!.closeDrawers()
            true
        }
    }
}