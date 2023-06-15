package com.example.stretchyourbody.data

import java.io.Serializable

data class Cwiczenie(val zdjecie: String? = null,
                     val nazwaCwiczenia: String? = null,
                     val czasCwiczenia: Int? = null,): Serializable {
                    constructor() : this("", "", 0)
                }
