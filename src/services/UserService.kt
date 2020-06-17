package com.medtracker.services

import com.medtracker.models.User
import com.medtracker.repositories.UserRepository
import com.medtracker.services.dto.LoginFDTO
import com.medtracker.services.dto.UserFDTO
import com.medtracker.services.validators.UserValidator
import com.medtracker.utilities.AuthenticationException
import io.ktor.auth.UnauthorizedResponse
import java.lang.Exception
import java.lang.IllegalArgumentException

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

        if (userRepository.emailExists(user.email!!)) throw IllegalArgumentException("Email is already in use.")

        user.hashThePassword()

        userRepository.createNew(user)
    }

    /**
     * Validate the data of the [User] if it has the correct format.
     * Find the user by its [User.email] in the [UserRepository].
     * Verify the [User] plain text password with the hashed password of the stored user.
     * Set the data of the stored user into the [User].
     */
    fun login(user: User) {
        val userValidator = UserValidator()
        val userRepository = UserRepository()

        userValidator.validate(user)

        val foundUser = userRepository.findByEmail(user.email!!) ?: throw AuthenticationException()

        if(!foundUser.verifyPassword(user.password!!)) throw AuthenticationException()

        user.id = foundUser.id
        user.password = foundUser.password
    }

    fun parseLoginFDTO(loginFDTO: LoginFDTO): User {
        return User(
            email = loginFDTO.email,
            password = loginFDTO.password
        )
    }

    fun parseUserFDTO(userFDTO: UserFDTO): User {
        return User(
            username = userFDTO.username,
            email = userFDTO.email,
            password = userFDTO.password,
            birthday = userFDTO.birthday
        )
    }
}