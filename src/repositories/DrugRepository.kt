package com.medtracker.repositories

import com.medtracker.models.Drug
import com.medtracker.repositories.dao.DrugDAO
import com.medtracker.repositories.dao.UserDAO
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DrugRepository{

    fun getAllByCreators(creatorIds: ArrayList<Int>): ArrayList<Drug>{
        val drugs: ArrayList<Drug> = arrayListOf()
        transaction {
            DrugDAO.select{DrugDAO.creatorID inList creatorIds}.map {
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

}

