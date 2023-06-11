package com.example.stretchyourbody

class TrainingsList {
    val trainings: ArrayList<Training> = ArrayList()

    init {
        //    TODO: tu chyba powinna być łączenie z lokalną baza danych?
        trainings.add(Training("Pierwszy trening", 30, "opis pierwszego treningu" +
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        trainings.add(Training("Drugi trening", 30, "opis drugiego treningu"))
        trainings.add(Training("Trzeci trening", 30, "opis trzeciego treningu"))
        trainings[0].addExercise(Exercise("ćwiczenie 1", "opis", R.drawable.ex1), 10)
        trainings[0].addExercise(Exercise("ćwiczenie 2", "opis", R.drawable.ex2), 10)
        trainings[0].addExercise(Exercise("ćwiczenie 3", "opis", R.drawable.ex3), 10)
    }


    fun addTraining(training: Training) {
        trainings.add(training)
    }

    fun getAllTrainingsNames(): List<String> {
        return trainings.map { it.getTitle() }
    }

    fun getTraining(title: String): Training? {
        for (training in trainings) {
            if (training.getTitle() == title) {
                return training
            }
        }
        return null
    }
}