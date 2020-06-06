package com.medtracker.repositories

import com.medtracker.models.Drug
import com.medtracker.models.Brand
import com.medtracker.models.Source
import com.medtracker.models.User
import com.medtracker.repositories.dao.BrandDAO
import com.medtracker.repositories.dao.DrugDAO
import com.medtracker.repositories.dao.UserDAO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DrugRepository {

    fun getAllByCreator(creatorId: Int, withVerified: Boolean, includedResources: List<String>): ArrayList<Drug> {
        val drugs: ArrayList<Drug> = arrayListOf()

        transaction {
            val results = DrugDAO.select { DrugDAO.creatorId eq creatorId }

            if (withVerified)
                results.adjustColumnSet { innerJoin(UserDAO) }
                    .orWhere { UserDAO.verified eq true }

            /**
             * @todo
             * - leftjoins voor:
             *      - source
             *      - creator
             *      - drugcomponents
             */

            if (includedResources.contains("brands"))
                results.adjustColumnSet { leftJoin(BrandDAO) }
                    .adjustSlice { slice(fields + BrandDAO.columns) }

            results.map {
                val drug = Drug(
                    id = it[DrugDAO.id],
                    name = it[DrugDAO.name],
                    thumbnailURL = it[DrugDAO.thumbnailURL],
                    source = Source(id = it[DrugDAO.sourceId]),
                    brand = Brand(id = it[DrugDAO.creatorId]),
                    creator = User(id = it[DrugDAO.creatorId])
                )

                if (includedResources.contains("brands"))
                    drug.brand?.name = it[BrandDAO.name]

                /**
                 * @todo
                 * - data toevoegen aan:
                 *      - source
                 *      - creator
                 *      - drugcomponents
                 *  Hierboven refacoteren dat het gelijk bij de initialisatie gedaan wordt
                 */

                drugs.add(drug)
            }
        }

        return drugs
    }
}

