package com.example.stretchyourbody

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrainingActivity : AppCompatActivity() {
    private lateinit var trainingName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        trainingName = intent.getStringExtra("trainingName").toString()
        Log.d("lista", "Training Name: $trainingName")
        val titleView: TextView = findViewById(R.id.training_name) as TextView
        titleView.text = trainingName
    }
}
