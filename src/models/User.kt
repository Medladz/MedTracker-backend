package com.medtracker.models

import io.ktor.auth.Principal
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
) : Principal {
    var birthday: DateTime? = birthday?.parseBirthday()

    /**
     * Parse the [birthday] to a [DateTime] instance
     */
    private fun String.parseBirthday(): DateTime {
        try {
            return DateTime.parse(this)
        } catch (e: Exception) {
            throw IllegalArgumentException("Birthday needs to be in format YYYY-MM-DD.")
        }
    }

    /**
     * Hash the [password] using the [BCrypt] algorithm and set it as [password].
     */
    fun hashThePassword() {
        password = BCrypt.hashpw(password, BCrypt.gensalt()) ?: throw Exception("Password couldn't be hashed")
    }

    /**
     * Verify [passwordToVerify] if it is the same as the hashed [password]
     */
    fun verifyPassword(passwordToVerify: String): Boolean {
        try {
            return BCrypt.checkpw(passwordToVerify, password)
        } catch (e: Exception) {
            throw Exception()
        }
    }
}

