package com.medtracker.controllers

import com.medtracker.models.Drug
import com.medtracker.models.DrugDTO
import com.medtracker.repositories.DrugDAO
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class DrugController {

    fun getAll(): ArrayList<Drug> {
        val drugs: ArrayList<Drug> = arrayListOf()
        transaction {
            DrugDAO.selectAll().map {
                drugs.add(
                    Drug(
                        id = it[DrugDAO.id],
                        creatorID = it[DrugDAO.creatorID],
                        brandID = it[DrugDAO.brandID],
                        sourceID = it[DrugDAO.sourceID],
                        name = it[DrugDAO.name],
                        thumbnailURL = it[DrugDAO.thumbnailURL]
                    )
                )
            }
        }

        return drugs
    }
    fun insert(drug: DrugDTO) {

        transaction {
            DrugDAO.insert {
                it[creatorID] = drug.creatorID
                it[brandID] = drug.brandID
                it[sourceID] = drug.sourceID
                it[name] = drug.name
                it[thumbnailURL] = drug.thumbnailURL
            }
        }
    }

    fun update(drug: DrugDTO, ID: Int) {
        transaction {
            DrugDAO.update({ DrugDAO.id eq ID }) {
                it[creatorID] = drug.creatorID
                it[brandID] = drug.brandID
                it[sourceID] = drug.sourceID
                it[name] = drug.name
                it[thumbnailURL] = drug.thumbnailURL
            }
        }
    }

    fun delete(ID: Int) {
        transaction {
            DrugDAO.deleteWhere { DrugDAO.id eq ID }
        }
    }
}