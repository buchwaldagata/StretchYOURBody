package com.example.stretchyourbody

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.stretchyourbody.data.Cwiczenie
import com.example.stretchyourbody.data.Trening
import com.example.stretchyourbody.data.User
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class AddExercisesToTrainingActivity : AppCompatActivity() {

    private lateinit var user: User
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
    private val editTextCustomExerciseTime:EditText
        get() = findViewById(R.id.editTextCustomExerciseTime)
    private val editTextCustomExerciseTitle:EditText
        get() = findViewById(R.id.editTextCustomExerciseTitle)
//    private val imageViewCustomExercise:ImageView
//        get() = findViewById(R.id.imageViewCustomExercise)
    private val customExerciseLayout: LinearLayout
        get() = findViewById(R.id.customExerciseLayout)
    private val exercisesList = ExercisesList()
    private var isCustomExerciseFormVisible = false

    var exercises: MutableList<Cwiczenie> = mutableListOf()

    private lateinit var training: Trening


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercises_to_training)

        // Odczytanie danych z poprzedniej aktywności
        val intent = intent
        user = intent.getSerializableExtra("user")!! as User
        val name = intent.getStringExtra("name")!!
//        val description = intent.getStringExtra("description")!!
        val level = intent.getStringExtra("level")!!
        FirebaseApp.initializeApp(this)
        // Utworzenie obiektu Training

        buttonAddExercise.setOnClickListener {
            if (validateFields()) {
                addExercise()
            } else {
                showAlertDialog("Uzupełnij wszystkie pola!")
            }
        }

        buttonFinish.setOnClickListener {
            if (exercises.isEmpty()) {
                showAlertDialog("Dodaj co najmniej jedno ćwiczenie!")
            } else {
                training = Trening(name, level, exercises)
                finishAddingTraining(training)
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
//        exercisesList.getExercise(exerciseName)?.let { training.addExercise(it, exerciseDuration) }
        exercises.add(Cwiczenie(exerciseName, exerciseDuration))

        // Dodanie wiersza z ćwiczeniem do tabeli
        addExerciseBasic(exerciseDuration, exerciseName)

        editTextExerciseDuration.text.clear()
        setupSpinner()
    }


    private fun addExerciseBasic(exerciseDuration: Int, exerciseName: String){
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
//        training.removeExercise(index)
        exercises.removeAt(index)

        // Usunięcie wiersza z tabeli
        tableLayoutExercises.removeView(row)
    }

    private fun finishAddingTraining(training: Trening) {
        Log.e("dodawanie", "dziala")
//        val trainings: TrainingsList = TrainingsList()
//        trainings.addTraining(training)

//        TODO: Dodać training do bazy

        user.treningi.add(training)

        val fs = Firebase.firestore
        fs.collection("users").document(user.uid!!).set(user)
//        val usersCollection = db.collection("users")
//        usersCollection.add(user)
//        val database = FirebaseDatabase.getInstance()
//        val usersRef = database.getReference("users")
//        val newUserRef = usersRef.push()
//        newUserRef.setValue(user)
//        usersRef.



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
        val exerciseTime = editTextCustomExerciseTime.text.toString()

        if (exerciseTitle.isNotEmpty() && exerciseTime.isNotEmpty()) {
            val exercise = Cwiczenie(exerciseTitle, exerciseTime.toInt())
            exercises.add(exercise)

            addExerciseBasic(exerciseTime.toInt(), exerciseTitle)

            setupSpinner()
            editTextCustomExerciseTitle.text.clear()
            editTextCustomExerciseTime.text.clear()
            customExerciseLayout.visibility = View.GONE
            isCustomExerciseFormVisible = false
            Toast.makeText(this, "Dodano własne ćwiczenie", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
        }
    }


}

