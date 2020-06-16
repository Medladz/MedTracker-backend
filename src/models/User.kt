package com.medtracker.models

import org.joda.time.DateTime
import org.mindrot.jbcrypt.BCrypt
import java.lang.Exception
import java.lang.IllegalArgumentException

class User(
    var id: Int? = null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var verified: Boolean? = null,
    birthday: String? = null
) {
    var birthday: DateTime? = birthday?.parseBirthday()

    // Parse birthday string to datetime
    private fun String.parseBirthday(): DateTime{
        try{
            return DateTime.parse(this)
        } catch (e: Exception) {
            throw IllegalArgumentException("Birthday needs to be in format YYYY-MM-DD.")
        }
    }

    // hash the password using the BCrypt algorithm and set it
    fun hashThePassword() {
        password = BCrypt.hashpw(password, BCrypt.gensalt() ) ?: throw Exception("Password couldn't be hashed")
    }
}

