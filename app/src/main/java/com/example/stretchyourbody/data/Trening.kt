package com.example.stretchyourbody.data

data class Trening( val nazwaTreningu:String? = null,
                    val czasTrwania: Int? = null,
                    val czasPomiedzyCwiczeniami: Int? = null,
                    val poziomTrudnosci: String? = null,
                    val cwiczenia: List<Cwiczenie>

                    )
