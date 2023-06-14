package com.example.stretchyourbody

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.stretchyourbody.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore = FirebaseFirestore.getInstance()


        val cloudResult = MutableLiveData<User?>()
        val uid = firebaseAuth.currentUser?.uid
//        val db = Firebase.firestore


//        if (uid != null) {

        // Tutaj możesz użyć pobranego UID użytkownika
//            val docRef = db.collection("users").document(uid)

//            docRef.get().addOnSuccessListener { documentSnapshot ->
//                val city = documentSnapshot.toObject<User>()
//                val napis = "Przykładowy napis"
//            }

        firebaseFirestore.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user)
            }
            .addOnFailureListener{
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
}