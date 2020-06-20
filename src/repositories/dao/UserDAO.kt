package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date
import org.joda.time.DateTime

object UserDAO : Table("user") {
    val id: Column<Int> = integer("\"ID\"").autoIncrement("user_ID_seq")
    val username: Column<String> = varchar("username", 255)
    val email: Column<String> = varchar("email", 255).uniqueIndex()
    val password: Column<String> = varchar("password", 255)
    val verified: Column<Boolean> = bool("verified")
    val birthday: Column<DateTime> = date("birthday")

    override val primaryKey = PrimaryKey(id)
}