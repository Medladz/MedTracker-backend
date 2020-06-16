package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FavoritedDrugDAO : Table("\"favoritedDrug\""){
    val drugId: Column<Int> = integer("drugID").references(DrugDAO.id)
    val userId: Column<Int> = integer("userID").references(UserDAO.id)
    val order: Column<Short> = short("order")

    override val primaryKey = PrimaryKey(drugId, userId)
}