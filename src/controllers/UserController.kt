package com.medtracker.controllers

import com.medtracker.models.User
import com.medtracker.models.UserDTO
import com.medtracker.repositories.dao.UserDAO
import com.medtracker.services.DrugService
import com.medtracker.services.UserService
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


class UserController {

    fun insert(user: UserDTO) {

        transaction {
            UserDAO.insert {
                it[username] = user.username
                it[email] = user.email
                it[password] = user.password
                it[verified] = user.verified
                it[birthday] = DateTime.parse(user.birthday)
            }
        }
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