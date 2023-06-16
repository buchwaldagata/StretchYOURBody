package com.example.stretchyourbody

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.stretchyourbody.data.Trening

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
    private val buttonAddCustomExercise:Button
        get() = findViewById(R.id.buttonAddCustomExercise)
    private val buttonSaveCustomExercise:Button
        get() = findViewById(R.id.buttonSaveCustomExercise)
    private val editTextCustomExerciseDescription:EditText
        get() = findViewById(R.id.editTextCustomExerciseDescription)
    private val editTextCustomExerciseTitle:EditText
        get() = findViewById(R.id.editTextCustomExerciseTitle)
    private val imageViewCustomExercise:ImageView
        get() = findViewById(R.id.imageViewCustomExercise)
    private val customExerciseLayout: LinearLayout
        get() = findViewById(R.id.customExerciseLayout)
    private val exercisesList = ExercisesList()
    private var isCustomExerciseFormVisible = false


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
        training = Trening(name, duration, description)

        buttonAddExercise.setOnClickListener {
            if (validateFields()) {
                addExercise()
            } else {
                showAlertDialog("Uzupełnij wszystkie pola!")
            }
        }

        buttonFinish.setOnClickListener {
            if (training.getExercises().isEmpty()) {
                showAlertDialog("Dodaj co najmniej jedno ćwiczenie!")
            } else {
                finishAddingTraining()
            }
        }

        setupSpinner()

//        imageViewCustomExercise.setOnClickListener {
//
//        }

        buttonAddCustomExercise.setOnClickListener {
            toggleCustomExerciseForm()
        }

        buttonSaveCustomExercise.setOnClickListener {
            saveCustomExercise()
        }

    }

    private fun setupSpinner() {
        val exerciseList = exercisesList.getAllExercisesNames().toMutableList()
        exerciseList.add(0, "Wybierz ćwiczenie")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, exerciseList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

        editTextExerciseDuration.text.clear()
        setupSpinner()
    }

    private fun removeExercise(row: TableRow) {
        // Usunięcie ćwiczenia z obiektu Training
        val index = tableLayoutExercises.indexOfChild(row) - 1 // Pomijamy wiersz nagłówków
        training.removeExercise(index)

        // Usunięcie wiersza z tabeli
        tableLayoutExercises.removeView(row)
    }

    private fun finishAddingTraining() {
        Log.e("dodawanie", "dziala")
        val trainings: TrainingsList = TrainingsList()
        trainings.addTraining(training)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun validateFields(): Boolean {
        val exerciseDuration = editTextExerciseDuration.text.toString().trim()
        val exerciseName = spinnerExercises.selectedItem.toString()

        return (exerciseDuration.isNotEmpty() && exerciseName != "Wybierz ćwiczenie")
    }

    private fun showAlertDialog(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun toggleCustomExerciseForm() {
        if (isCustomExerciseFormVisible) {
            customExerciseLayout.visibility = View.GONE
            isCustomExerciseFormVisible = false
        } else {
            customExerciseLayout.visibility = View.VISIBLE
            isCustomExerciseFormVisible = true
        }
    }

    private fun saveCustomExercise() {
        val exerciseTitle = editTextCustomExerciseTitle.text.toString()
        val exerciseDescription = editTextCustomExerciseDescription.text.toString()

        if (exerciseTitle.isNotEmpty() && exerciseDescription.isNotEmpty()) {
            val exercise = Exercise(exerciseTitle, exerciseDescription, R.drawable.default_img)
            exercisesList.addExercise(exercise)
            setupSpinner()
            editTextCustomExerciseTitle.text.clear()
            editTextCustomExerciseDescription.text.clear()
            customExerciseLayout.visibility = View.GONE
            isCustomExerciseFormVisible = false
            Toast.makeText(this, "Dodano własne ćwiczenie", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
        }
    }


}

