package io.viper.android.todolist.addedittask

import android.app.Activity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.viper.android.todolist.R


/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
class AddEditTaskFragment : Fragment(), AddEditTaskContract.View {
    companion object {
        val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"

        fun newInstance(): AddEditTaskFragment {
            return AddEditTaskFragment()
        }
    }

    private var mPresenter: AddEditTaskContract.Presenter? = null
    private var mTitle: TextView? = null
    private var mDescription: TextView? = null

    override fun onResume() {
        super.onResume()
        mPresenter!!.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab_edit_task_done)
        fab.setImageResource(R.drawable.ic_done)
        fab.setOnClickListener {
            mPresenter!!.saveTask(mTitle!!.text.toString(), mDescription!!.text.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater!!.inflate(R.layout.addtask_frag, container, false)
        mTitle = root.findViewById(R.id.add_task_title) as TextView
        mDescription = root.findViewById(R.id.add_task_description) as TextView
        setHasOptionsMenu(true)
        return root
    }

    override fun showEmptyTaskError() {
        Snackbar.make(mTitle!!, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show()
    }

    override fun showTasksList() {
        requireActivity().setResult(Activity.RESULT_OK)
        requireActivity().finish()
    }

    override fun setTitle(title: String) {
        mTitle!!.text = title
    }

    override fun setDescription(description: String) {
        mDescription!!.text = description
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: AddEditTaskContract.Presenter) {
        mPresenter = presenter
    }
}