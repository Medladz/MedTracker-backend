package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DrugContainerDAO : Table("\"drugContainer\""){
    val drugId: Column<Int> = integer("drugID").references(DrugDAO.id).primaryKey(0)
    val containerId: Column<Int> = integer("containerID").references(ContainerDAO.id).primaryKey(1)
}