package io.viper.android.todolist.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class ActivityUtils{
    companion object{
        fun addFragmentToActivity(fragmentManager: FragmentManager,
                                  fragment: Fragment,
                                  frameId: Int) {
            val transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment)
            transaction.commit()
        }
    }
}

