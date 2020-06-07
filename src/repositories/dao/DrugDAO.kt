package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DrugDAO : Table("drug") {
    val id: Column<Int> = integer("\"ID\"").primaryKey()
    val creatorId: Column<Int> = integer("creatorID").references(UserDAO.id)
    val brandId: Column<Int?> = integer("brandID").references(BrandDAO.id).nullable()
    val sourceId: Column<Int?> = integer("sourceID").references(SourceDAO.id).nullable()
    val name: Column<String> = varchar("name", 255)
    val thumbnailURL: Column<String?> = varchar("thumbnailURL", 255).nullable()
}