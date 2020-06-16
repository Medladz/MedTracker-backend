package com.medtracker.services.validators

import com.medtracker.models.User
import com.medtracker.utilities.Regexes

class UserValidator{
    fun validate(user : User){
        require(user.username!!.isNotEmpty()) {"Username can't be empty."}
        require(user.email!!.isNotEmpty()) {"Email can't be empty."}
        require(Regexes.emailValidation.matches(user.email!!)) {"Email is not in correct format"}
        require(user.password!!.isNotEmpty()) {"password can't be empty."}
    }
}