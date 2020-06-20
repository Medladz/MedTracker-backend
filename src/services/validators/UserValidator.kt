package com.medtracker.services.validators

import com.medtracker.models.User
import com.medtracker.utilities.Regexes

class UserValidator {
    fun validate(user: User) {
        user.username?.let { require(user.username!!.isNotEmpty()) { "Username can't be empty." } }
        user.email?.let {
            require(user.email!!.isNotEmpty()) { "Email can't be empty." }
            require(Regexes.emailValidation.matches(user.email!!)) { "Email is not in the correct format." }
        }
        user.password?.let { require(user.password!!.isNotEmpty()) { "Password can't be empty." } }
    }
}