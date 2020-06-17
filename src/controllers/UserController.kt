package com.medtracker.controllers

import com.medtracker.models.User
import com.medtracker.services.JWTAuth
import com.medtracker.services.UserService
import com.medtracker.services.dto.AuthDTO
import com.medtracker.services.dto.LoginFDTO
import com.medtracker.services.dto.UserFDTO
import com.medtracker.services.responseParsers.AuthParser
import com.medtracker.utilities.UnprocessableEntityException
import java.lang.IllegalArgumentException

class UserController {

    /**
     * Parse the [userFDTO] to a [User].
     * Use the [UserService] to create a new user record.
     * Use the [AuthParser] to send back the [AuthDTO] with the generated JWT.
     */
    fun createNew(userFDTO: UserFDTO): AuthDTO {
        try {
            val userService = UserService()
            val authParser = AuthParser()
            val user = userService.parseUserFDTO(userFDTO)

            userService.createNew(user)

            return authParser.parse(JWTAuth.generate(user))
        } catch (e: Exception) {
            when(e){
                is IllegalArgumentException -> throw UnprocessableEntityException(e.message)
                else -> throw e
            }
        }
    }

    /**
     * Parse the [loginFDTO] to a [User].
     * Use this model to login the user with the [UserService].
     * Use the [AuthParser] to send back the [AuthDTO] with the generated JWT.
     */
    fun login(loginFDTO: LoginFDTO): AuthDTO {
        val userService = UserService()
        val authParser = AuthParser();

        val user: User = userService.parseLoginFDTO(loginFDTO)

        userService.login(user)

        return authParser.parse(JWTAuth.generate(user))
    }

//    fun update(user: UserDTO, id: Int) {

//        transaction {
//            UserDAO.update({ UserDAO.id eq id }) {
//                it[username] = user.username
//                it[email] = user.email
//                it[password] = user.password
//                it[verfied] = user.verfied
//                it[birthdate] = DateTime.parse(user.birthdate)
//            }
//        }
//    }

//    fun delete(id: Int) {
//        transaction {
//            UserDAO.deleteWhere { UserDAO.id eq id }
//        }
//    }
}