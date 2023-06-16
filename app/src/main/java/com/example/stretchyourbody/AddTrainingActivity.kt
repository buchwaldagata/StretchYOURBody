package com.example.stretchyourbody

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.auth.User
import java.io.Serializable


class AddTrainingActivity : AppCompatActivity() {


    private lateinit var editTextName: EditText
//    private lateinit var editTextDescription: EditText
    private lateinit var editTextLevel: EditText
    private lateinit var user: Serializable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_training)

        val intent = intent
        user = intent.getSerializableExtra("user")!!
        editTextName = findViewById(R.id.editTextName)
//        editTextDescription = findViewById(R.id.editTextDescription)
        editTextLevel= findViewById(R.id.editTextLevel)

        val buttonNext = findViewById<Button>(R.id.buttonNext)
        buttonNext.setOnClickListener {
            if (validateFields()) {
                openAddExercisesActivity()
            } else {
                showAlertDialog("Uzupełnij wszystkie pola!")
            }
        }
    }

    private fun validateFields(): Boolean {
        val name = editTextName.text.toString().trim()
//        val description = editTextDescription.text.toString().trim()
        val level = editTextLevel.text.toString().trim()

        return name.isNotEmpty()
//                && description.isNotEmpty()
                && level.isNotEmpty()
    }


    private fun openAddExercisesActivity() {
        val name = editTextName.text.toString()
//        val description = editTextDescription.text.toString()
        val level = editTextLevel.text.toString()

        val intent = Intent(this, AddExercisesToTrainingActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("user", user)
        intent.putExtra("level", level)
        startActivity(intent)
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
}
