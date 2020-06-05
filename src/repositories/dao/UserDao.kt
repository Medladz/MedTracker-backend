package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Table


object UserDAO : Table("user") {
    val id = integer("id").primaryKey()
    val username = text("username")
    val email = text("email")
    val password = text("password")
    val verfied = bool("verfied")
    val birthdate = date("birthdate")
}
