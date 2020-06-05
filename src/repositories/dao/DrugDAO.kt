package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Table

object DrugDAO : Table("drug") {
    val id = integer("id").primaryKey()
    val creatorID = integer("creatorID")
    val brandID = integer("brandID")
    val sourceID = integer("sourceID")
    val name = text("name")
    val thumbnailURL = text("thumbnailURL")
}