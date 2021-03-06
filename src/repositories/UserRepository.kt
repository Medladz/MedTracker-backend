package com.medtracker.repositories

import com.medtracker.models.User
import com.medtracker.repositories.dao.UserDAO
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.lang.Exception
import kotlin.system.exitProcess


class UserRepository {

    // Check if the given email exists in the database
    fun emailExists(email: String): Boolean {
        return transaction {
            return@transaction UserDAO.select { UserDAO.email eq email }.count() > 0
        }
    }

    /**
     * Uses the [email] to fetch it's user record from the database.
     * Convert the user record to [User]
     */
    fun findByEmail(email: String): User? {
        return transaction {
            val userRecord = UserDAO.select{ UserDAO.email eq email}.firstOrNull() ?: return@transaction null

            User(
                id = userRecord[UserDAO.id],
                password = userRecord[UserDAO.password]
            )
        }
    }

    /**
     * Create a new user record with the User model.
     * Set the inserted id into the model.
     */
    fun createNew(user: User) {
        try {
            transaction {
                UserDAO.insert {
                    it[username] = user.username!!
                    it[email] = user.email!!
                    it[password] = user.password!!
                    it[verified] = user.verified ?: false
                    it[birthday] = user.birthday!!
                }

                // @todo change when fix is released
                val userId = UserDAO.select { UserDAO.email eq (user.email!!) }.firstOrNull()?.get(UserDAO.id)

                user.id = userId
            }
        } catch (e: Exception) {
            throw Exception("User could not be inserted.")
        }
    }
}
