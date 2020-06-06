package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object UserDAO : Table("user") {
    val id: Column<Int> = integer("\"ID\"").primaryKey()
    val username: Column<String> = varchar("username", 255).uniqueIndex()
    val email: Column<String> = varchar("email", 255).uniqueIndex()
    val password: Column<String> = varchar("password", 255)
    val verified: Column<Boolean> = bool("verified")
    val birthday: Column<DateTime> = date("birthday")
}