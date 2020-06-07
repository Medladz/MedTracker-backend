package com.medtracker.services

import com.medtracker.models.Drug
import com.medtracker.repositories.DrugRepository

class DrugService {

    fun getAllByCreator(creatorId: Int, withVerified: Boolean, includedResources: List<String>?): ArrayList<Drug> {
        val drugRepository = DrugRepository()

        return drugRepository.getAllByCreator(creatorId, withVerified, includedResources)
    }


    // @todo verplaatsen naar repository
//    fun createNewDrug() {
//
//        transaction {
//            DrugDAO.insert {
//                it[creatorID] = drug.creatorID
//                it[brandID] = drug.brandID
//                it[sourceID] = drug.sourceID
//                it[name] = drug.name
//                it[thumbnailURL] = drug.thumbnailURL
//            }
//        }
//    }

}