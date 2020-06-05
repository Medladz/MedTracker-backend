package com.medtracker.services

import com.medtracker.models.Drug
import com.medtracker.repositories.DrugDAO
import com.medtracker.repositories.DrugRepository
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DrugService{


    fun getAllByCreators(creatorIds: ArrayList<Int>): ArrayList<Drug>{
        val drugRepository = DrugRepository()
        return drugRepository.getAllByCreators(creatorIds)
    }
    fun createNewDrug(){

//        transaction {
//            DrugDAO.insert {
//                it[creatorID] = drug.creatorID
//                it[brandID] = drug.brandID
//                it[sourceID] = drug.sourceID
//                it[name] = drug.name
//                it[thumbnailURL] = drug.thumbnailURL
//            }
//        }
    }

}