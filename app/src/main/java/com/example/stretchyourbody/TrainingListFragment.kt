package com.example.stretchyourbody

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class TrainingListFragment : ListFragment() {
    val trainings: TrainingsList = TrainingsList()
    var trainingNames = trainings.getAllTrainingsNames()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trainingNames = trainings.getAllTrainingsNames()
        val adapter = ArrayAdapter(
            inflater.context, R.layout.simple_list_item_1, trainingNames
        )
        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    internal interface Listener {
        fun itemClicked(id: Long)
    }

    private var listener: Listener? = null
//    fun onAttach(context: Context?) {
//        super.onAttach(context!!)
//        listener = context as Listener?
//    }
//
//    fun onListItemClick(listView: ListView?, itemView: View?, position: Int, id: Long) {
//        if (listener != null) {
//            listener!!.itemClicked(id)
//        }
//    }
}
