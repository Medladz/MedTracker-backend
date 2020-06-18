package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column

object BrandDAO : Table("brand") {
    val id: Column<Int> = integer("\"ID\"")
    val creatorId: Column<Int> = integer("creatorID").references(UserDAO.id)
    val name: Column<String> = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(creatorId, name)
    }
}