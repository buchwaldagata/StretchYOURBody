package com.example.stretchyourbody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.stretchyourbody.data.Cwiczenie
import com.example.stretchyourbody.data.Trening
import kotlin.properties.Delegates

class ExerciseActivity : AppCompatActivity() {
    var seconds by Delegates.notNull<Int>()
    private var running = true
    private var wasRunning = false
    lateinit var training: Trening
    lateinit var exercises: List<Cwiczenie>
    var index by Delegates.notNull<Int>()


    private val imageView:ImageView
        get() = findViewById(R.id.exercise_image)
    private val timeView: TextView
        get() = findViewById(R.id.time_view)
    private val titleView: TextView
        get() = findViewById(R.id.exercise_title)
    private val descriptionView: TextView
        get() = findViewById(R.id.exercise_description)
    private val startButton:Button
        get() = findViewById(R.id.start_button)
    private val stopButton:Button
        get() = findViewById(R.id.stop_button)
    private val resetButton: Button
        get() = findViewById(R.id.reset_button)


    override fun onCreate(savedInstanceState: Bundle?) {
        training = intent.getSerializableExtra("training") as Trening
        index = (intent.getStringExtra("exerciseIndex")?.toInt())!!
        exercises = training.cwiczenia
        var exercise: Cwiczenie = exercises[index]
        var time: Int = exercise.czasCwiczenia!!
        seconds = time

        setContentView(R.layout.activity_exercise)
        startButton.setOnClickListener { running = true }
        stopButton.setOnClickListener { running = false }
        resetButton.setOnClickListener {
            running = false
            seconds = time
        }

        imageView.setImageResource(Images.images.get(index%Images.images.size))
        titleView.setText(exercise.nazwaCwiczenia)
//        descriptionView.setText(exercise.)


        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }

        runStoper(timeView)
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }


    fun runStoper(timeView: TextView) {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                val time = String.format("%d:%02d:%02d", hours, minutes, secs)
                timeView.text = time
                if (running) {
                    seconds--
                    if (seconds == 0) {
                        running = false
                        newExercise()
                    }
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun newExercise() {
        if (index < exercises.size - 1) {
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("training", training)
            intent.putExtra("exerciseIndex", (index+1).toString())
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}