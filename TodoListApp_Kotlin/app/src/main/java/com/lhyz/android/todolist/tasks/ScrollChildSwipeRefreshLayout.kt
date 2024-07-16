package com.lhyz.android.todolist.tasks

import android.content.Context
import androidx.core.view.ViewCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class ScrollChildSwipeRefreshLayout : SwipeRefreshLayout {

    var mScrollUpChild: View? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun canChildScrollUp(): Boolean {
        if (mScrollUpChild != null) {
            return ViewCompat.canScrollVertically(mScrollUpChild, -1)
        }
        return super.canChildScrollUp()
    }

    fun setScrollUpChild(view: View) {
        mScrollUpChild = view
    }
}