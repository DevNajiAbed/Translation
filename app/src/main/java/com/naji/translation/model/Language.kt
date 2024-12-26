package com.naji.translation.model

import java.io.Serializable

data class Language(
    val code: String,
    val name: String
) : Serializable