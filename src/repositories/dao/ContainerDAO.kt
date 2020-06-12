package com.medtracker.repositories.dao

import com.medtracker.repositories.dao.DrugComponentDAO.nullable
import com.medtracker.repositories.enumTypes.WeightOrVolume
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ContainerDAO : Table("container"){
    val id: Column<Int> = integer("\"ID\"").primaryKey()
    val creatorId: Column<Int> = integer("creatorID").references(UserDAO.id)
    val name: Column<String> = varchar("name", 255)
    val quantity: Column<Int?> = integer("quantity").nullable()
    val measurementUnit: Column<WeightOrVolume?> = WeightOrVolume.pgColumn(this,"measurementUnit").nullable()
    val thumbnailURL: Column<String?> = varchar("thumbnailURL", 255).nullable()

    init {
        uniqueIndex(creatorId, name)
    }
}