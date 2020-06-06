package com.medtracker.models

import org.joda.time.DateTime
import java.util.*

class User(
    val id: Int? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val verified: Boolean? = null,
    val birthday: DateTime? = null
) {

}

// @todo dit moet naar dto map en RDTO en FDTO van maken
data class UserDTO(
    val username: String,
    val email: String,
    val password: String,
    val verified: Boolean,
    var birthday: String
)