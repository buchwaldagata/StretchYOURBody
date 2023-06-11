package com.example.stretchyourbody
class Training(private var title: String, private var duration: Int, private var description: String) {
    private val exercises = mutableListOf<Exercise>()
    private val exercisesTime = mutableListOf<Int>()

    fun getTitle(): String {
        return title
    }

    fun getDuration(): Int {
        return duration
    }

    fun getDescription(): String {
        return description
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDuration(duration: Int) {
        this.duration = duration
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getExercises(): MutableList<Exercise> {
        return exercises
    }

    fun addExercise(exercise: Exercise, duration: Int, index: Int = exercises.size) {
        exercises.add(index, exercise)
        exercisesTime.add(index, duration)
    }

    fun removeExercise(index: Int) {
        exercises.removeAt(index)
        exercisesTime.removeAt(index)
    }
}
