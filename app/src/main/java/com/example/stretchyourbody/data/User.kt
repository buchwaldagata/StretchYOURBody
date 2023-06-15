package com.example.stretchyourbody.data

import java.io.Serializable

data class User( val email:String? = null,
                val uid: String? = null
                ,val treningi: List<Trening>
                ): Serializable {
        constructor() : this("", "", emptyList())
    }
