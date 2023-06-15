package com.example.stretchyourbody

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.example.stretchyourbody.data.Trening
import com.example.stretchyourbody.data.User

class TrainingListFragment : ListFragment() {
    var user: User? = null
    var trainings: List<Trening> = listOf()
    var trainingNames: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user = arguments?.getSerializable("test") as User?
        trainings = user?.treningi ?: listOf()

        trainings.forEach {
            trainingNames.add(it.nazwaTreningu.toString())
        }

        val adapter = ArrayAdapter(
            inflater.context, R.layout.simple_list_item_1, trainingNames
        )
        listAdapter = adapter


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        Log.e("lista", "position" + position.toString())
        Log.e("lista", trainingNames[position])

        val trainingName = trainingNames[position]
        val intent = Intent(activity, TrainingStartActivity::class.java)
        intent.putExtra("trainingName", trainingName)
        startActivity(intent)
    }

}
