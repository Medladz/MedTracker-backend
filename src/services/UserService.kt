package com.medtracker.services

import com.medtracker.models.User
import com.medtracker.repositories.UserRepository
import com.medtracker.services.dto.UserFDTO
import com.medtracker.services.validators.UserValidator
import org.joda.time.DateTime
import java.lang.Exception
import java.lang.NullPointerException


class UserService {

    /**
     * Validate the user model.
     * Check if the email already exists.
     * Hash the password.
     * Use the user model to create a new user record.
     */
    fun createNew(user: User) {
        val userValidator = UserValidator()
        val userRepository = UserRepository()

        userValidator.validate(user)

        if(userRepository.emailExists(user.email!!)) throw UserEmailExists("Email is already in use.")

        user.hashThePassword()

        userRepository.createNew(user)
    }

    fun parseUserFDTO(userFDTO: UserFDTO): User {
        return User(
            username = userFDTO.username,
            email = userFDTO.email,
            password = userFDTO.password,
            birthday = userFDTO.birthday
        )
    }

    fun login(includedResources: List<String>?) {
        val userRepository = UserRepository()
        return userRepository.login(includedResources)
    }
}

class UserEmailExists(override val message: String?): Exception()