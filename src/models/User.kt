package com.medtracker.models

import org.joda.time.DateTime
import java.util.*


data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val verfied: Boolean,
    val birthdate: DateTime


)

data class UserDTO(
    val username: String,
    val email: String,
    val password: String,
    val verfied: Boolean,
    var birthdate: String
)