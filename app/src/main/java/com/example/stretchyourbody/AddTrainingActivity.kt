package com.example.stretchyourbody

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class AddTrainingActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDuration: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_training)

        editTextName = findViewById(R.id.editTextName)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextDuration = findViewById(R.id.editTextDuration)

        val buttonNext = findViewById<Button>(R.id.buttonNext)
        buttonNext.setOnClickListener {
            openAddExercisesActivity()
        }
    }

    private fun openAddExercisesActivity() {
        val name = editTextName.text.toString()
        val description = editTextDescription.text.toString()
        val duration = editTextDuration.text.toString().toInt()

        val intent = Intent(this, AddExercisesToTrainingActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("description", description)
        intent.putExtra("duration", duration)
        startActivity(intent)
    }
}
