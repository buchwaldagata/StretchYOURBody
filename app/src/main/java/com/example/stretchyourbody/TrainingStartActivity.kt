package com.example.stretchyourbody

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TrainingStartActivity : AppCompatActivity() {
    private lateinit var training: Training
    val trainings: TrainingsList = TrainingsList()
    private val imageView:ImageView
        get() = findViewById(R.id.exercise_image)
    private val nextButton:Button
        get() = findViewById(R.id.nextButton)
    private val previousButton:Button
        get() = findViewById(R.id.previousButton)
    private val startButton:FloatingActionButton
        get() = findViewById(R.id.startButton)

    private var exercises = mutableListOf<Exercise>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_start)
        training = trainings.getTraining(intent.getStringExtra("trainingName").toString())!!

        exercises = training.getExercises()

        val titleView: TextView = findViewById(R.id.training_name) as TextView
        titleView.text = training.getTitle()

        val descriptionView: TextView = findViewById(R.id.training_description) as TextView
        descriptionView.text = training.getDescription()

        var currentPosition = 0
        setImage(currentPosition)

        nextButton.setOnClickListener {
            currentPosition = (currentPosition + 1) % exercises.size
            setImage(currentPosition)
        }

        previousButton.setOnClickListener {
            currentPosition = if (currentPosition == 0) {
                exercises.size - 1
            } else {
                currentPosition - 1
            }
            setImage(currentPosition)
        }

        startButton.setOnClickListener {
            Log.i("AAA", training.getTitle())
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("trainingName", training.getTitle())
            intent.putExtra("exerciseIndex", "0")
            startActivity(intent)
        }

    }

    private fun setImage(position: Int) {
        if (exercises.size > 0) {
            imageView.setImageResource(exercises[position].getImage())
        }
    }


}
