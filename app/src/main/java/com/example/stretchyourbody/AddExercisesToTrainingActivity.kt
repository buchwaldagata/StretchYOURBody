package com.example.stretchyourbody

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExercisesToTrainingActivity : AppCompatActivity() {

    private val spinnerExercises:Spinner
        get() = findViewById(R.id.spinnerExercises)
    private val editTextExerciseDuration:EditText
        get() = findViewById(R.id.editTextExerciseDuration)
    private val tableLayoutExercises:TableLayout
        get() = findViewById(R.id.tableLayoutExercises)
    private val buttonAddExercise:Button
        get() = findViewById(R.id.buttonAddExercise)
    private val buttonFinish:Button
        get() = findViewById(R.id.buttonFinish)
    private val exercisesList = ExercisesList()


    private lateinit var training: Training

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercises_to_training)

        // Odczytanie danych z poprzedniej aktywności
        val intent = intent
        val name = intent.getStringExtra("name")!!
        val description = intent.getStringExtra("description")!!
        val duration = intent.getIntExtra("duration", 0)!!

        // Utworzenie obiektu Training
        training = Training(name, duration, description)

        buttonAddExercise.setOnClickListener {
            addExercise()
        }

        buttonFinish.setOnClickListener {
            finishAddingTraining()
        }

        val exerciseNames = exercisesList.getAllExercisesNames()

// Utwórz adapter dla Spinnera
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, exerciseNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Ustaw adapter dla Spinnera
        spinnerExercises.adapter = adapter
    }

    private fun addExercise() {
        val exerciseName = spinnerExercises.selectedItem.toString()
        val exerciseDuration = editTextExerciseDuration.text.toString().toInt()

        // Dodanie ćwiczenia do obiektu Training
        // Możesz dostosować indeks w zależności od preferowanej pozycji w tabeli
        exercisesList.getExercise(exerciseName)?.let { training.addExercise(it, exerciseDuration) }

        // Dodanie wiersza z ćwiczeniem do tabeli
        val row = TableRow(this)

        val nameTextView = TextView(this)
        nameTextView.text = exerciseName
        row.addView(nameTextView)

        val durationTextView = TextView(this)
        durationTextView.text = exerciseDuration.toString()
        row.addView(durationTextView)

        val removeButton = Button(this)
        removeButton.text = "Usuń"
        removeButton.setOnClickListener {
            removeExercise(row)
        }
        row.addView(removeButton)

        tableLayoutExercises.addView(row)
    }

    private fun removeExercise(row: TableRow) {
        // Usunięcie ćwiczenia z obiektu Training
        val index = tableLayoutExercises.indexOfChild(row) - 1 // Pomijamy wiersz nagłówków
        training.removeExercise(index)

        // Usunięcie wiersza z tabeli
        tableLayoutExercises.removeView(row)
    }

    private fun finishAddingTraining() {
        // Przekazanie obiektu Training do kolejnej aktywności lub innego miejsca w aplikacji
        // Możesz dostosować działanie w zależności od potrzeb
        // Przykład przekazania obiektu do nowej aktywności:
//        val intent = Intent(this, NextActivity::class.java)
//        intent.putExtra("training", training)
//        startActivity(intent)
        Log.e("dodawanie", "dziala")
        val trainings: TrainingsList = TrainingsList()
        trainings.addTraining(training)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

