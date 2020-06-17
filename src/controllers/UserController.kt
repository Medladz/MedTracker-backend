package com.medtracker.controllers

import com.medtracker.services.JWTAuth
import com.medtracker.services.UserService
import com.medtracker.services.dto.AuthDTO
import com.medtracker.services.dto.UserFDTO
import com.medtracker.services.responseParsers.AuthParser
import com.medtracker.services.validators.UserValidator
import java.lang.Exception

class UserController {

    // Parse the body parameters to the model and use the model to create a new user record.
    fun createNew(userFDTO: UserFDTO): AuthDTO {
        val userService = UserService()
        val authParser = AuthParser()

        val user = userService.parseUserFDTO(userFDTO)

        userService.createNew(user)

        return authParser.parse(JWTAuth.generate(user))
    }

    fun login(includedResources: List<String>?){
        val userService = UserService()
        val jwtToken= userService.login(includedResources)
        return jwtToken
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