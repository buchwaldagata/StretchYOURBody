package com.example.stretchyourbody

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.example.stretchyourbody.data.User
import com.example.stretchyourbody.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val addButton: FloatingActionButton
        get() = findViewById(R.id.add_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        var objectUser: User? = null
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore = FirebaseFirestore.getInstance()


        val cloudResult = MutableLiveData<User?>()
        val uid = firebaseAuth.currentUser?.uid

        firebaseFirestore.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener { querySnapshot ->

                if (querySnapshot.id == uid ){
                    objectUser = querySnapshot.toObject(User::class.java)
                    if (objectUser != null) {
                        Log.d("WYPIS", objectUser!!.email.toString())

                        val fragment = TrainingListFragment()
                        val args = Bundle()
                        args.putSerializable("user", objectUser)
                        fragment.arguments = args
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.training_list_container, fragment)
                            .commit()
                    }
                }

//                val textView = findViewById<TextView>(R.id.textView2)
//                textView.text = objectUser!!.treningi.toString()



            }
            .addOnFailureListener{
                Log.d("REPO_DEBUG", it.message.toString())
            }




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        addButton.setOnClickListener {
            val intent = Intent(this, AddTrainingActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }



}