package com.example.stretchyourbody

class ExercisesList {
    val exercises: ArrayList<Exercise> = ArrayList()

    init {
        addExercise(Exercise("ćwiczenie 1", "opis", R.drawable.ex1))
        addExercise(Exercise("ćwiczenie 2", "opis", R.drawable.ex2))
        addExercise(Exercise("ćwiczenie 3", "opis", R.drawable.ex3))
    }

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
    }

    fun getAllExercisesNames(): List<String> {
        return exercises.map { it.getTitle() }
    }

    fun getExercise(title: String): Exercise? {
        for (exercise in exercises) {
            if (exercise.getTitle() == title) {
                return exercise
            }
        }
        return null
    }
}