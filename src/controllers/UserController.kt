package com.medtracker.controllers

import com.medtracker.models.User
import com.medtracker.models.UserDTO
import com.medtracker.repositories.UserDAO
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


class UserController {

    fun getAll(): ArrayList<User> {
        val users: ArrayList<User> = arrayListOf()
        transaction {
            UserDAO.selectAll().map {
                users.add(
                    User(
                        id = it[UserDAO.id],
                        username = it[UserDAO.username],
                        email = it[UserDAO.email],
                        password = it[UserDAO.password],
                        verfied = it[UserDAO.verfied],
                        birthdate = it[UserDAO.birthdate]
                    )
                )
            }
        }

        return users
    }

    fun insert(user: UserDTO) {

        transaction {
            UserDAO.insert {
                it[username] = user.username
                it[email] = user.email
                it[password] = user.password
                it[verfied] = user.verfied
                it[birthdate] = DateTime.parse(user.birthdate)
            }
        }
    }

    fun update(user: UserDTO, id: Int) {

        transaction {
            UserDAO.update({ UserDAO.id eq id }) {
                it[username] = user.username
                it[email] = user.email
                it[password] = user.password
                it[verfied] = user.verfied
                it[birthdate] = DateTime.parse(user.birthdate)
            }
        }
    }

    fun delete(id: Int) {
        transaction {
            UserDAO.deleteWhere { UserDAO.id eq id }
        }
    }
}