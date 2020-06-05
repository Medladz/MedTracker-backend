package com.medtracker.repositories

import com.medtracker.models.User
import com.medtracker.repositories.dao.UserDAO
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    fun getAllVerifiedUserIds(): ArrayList<Int>{
        val userIds: ArrayList<Int> = arrayListOf()
        transaction {
            UserDAO.slice(UserDAO.id).select{UserDAO.verfied eq true}.map {
                userIds.add(it[UserDAO.id])
            }
        }

        return userIds
    }


}