package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column

object SourceDAO : Table("source") {
    val id: Column<Int> = integer("\"ID\"").primaryKey()
    val creatorId: Column<Int> = integer("creatorID").references(UserDAO.id)
    val name: Column<String> = varchar("name", 255)

    init {
        uniqueIndex(creatorId, name)
    }
}