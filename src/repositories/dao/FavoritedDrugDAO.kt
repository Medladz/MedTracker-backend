package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object FavoritedDrugDAO : Table("\"favoritedDrug\""){
    val drugId: Column<Int> = integer("drugID").references(DrugDAO.id).primaryKey(0)
    val userId: Column<Int> = integer("userID").references(UserDAO.id).primaryKey(1)
    val order: Column<Short> = short("order")
}