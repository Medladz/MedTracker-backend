package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ContainerDAO : Table("container"){
    val id: Column<Int> = integer("\"ID\"").primaryKey()
    val creatorId: Column<Int> = integer("creatorID").references(UserDAO.id)
    val name: Column<String> = varchar("name", 255)
    val quantity: Column<Int> = integer("quantity")
    // @todo change to ENUM weightOrVolume
    val measurementUnit: Column<String> = text("measurementUnit")
    val thumbnailURL: Column<String> = varchar("thumbnailURL", 255)

    init {
        uniqueIndex(creatorId, name)
    }
}