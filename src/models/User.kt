package com.medtracker.models

import org.joda.time.DateTime
import java.util.*

class User(
    var id: Int? = null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var verified: Boolean? = null,
    var birthday: DateTime? = null
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