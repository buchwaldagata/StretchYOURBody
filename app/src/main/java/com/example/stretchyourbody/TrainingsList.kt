package com.example.stretchyourbody

class TrainingsList {
    val trainings: ArrayList<Training> = ArrayList()

    init {
        //    TODO: tu chyba powinna być łączenie z lokalną baza danych?
        trainings.add(Training("Pierwszy trening", 30))
        trainings.add(Training("Drugi trening", 30))
        trainings.add(Training("Trzeci trening", 30))
    }


    fun addTraining(training: Training) {
        trainings.add(training)
    }

    fun getAllTrainingsNames(): List<String> {
        return trainings.map { it.getTitle() }
    }
}