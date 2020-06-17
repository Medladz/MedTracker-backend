package com.medtracker

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.jackson.*
import io.ktor.features.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import com.medtracker.controllers.AgendaController
import com.medtracker.controllers.DrugController
import com.medtracker.controllers.UserController
import com.medtracker.models.Agenda
import com.medtracker.models.User
import com.medtracker.repositories.dao.UserDAO
import com.medtracker.services.JWTAuth
import com.medtracker.services.UserEmailExists
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import com.medtracker.services.dto.AgendaFDTO
import com.medtracker.services.dto.AuthDTO
import com.medtracker.services.dto.UserFDTO
import io.ktor.auth.Authentication
import io.ktor.auth.Principal
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.http.HttpStatusCode
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.text.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    fun initDB() {
        val config = HikariConfig("/hikari.properties")
        config.schema = "public"

        val ds = HikariDataSource(config)

        Database.connect(ds)
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(Authentication) {
        jwt {
            verifier(JWTAuth.verifier)
            realm = JWTAuth.realm
            validate {
                val userId = it.payload.getClaim("userId").asInt()

                User(id = userId)
            }
        }
    }

    initDB()

    routing {
        authenticate {
            get("/creators/{creatorId}/drugs") {
                try {
                    val drugController = DrugController()

                    val creatorId = call.parameters["creatorId"]?.toInt() ?: throw IllegalArgumentException()
                    val withVerified = call.request.queryParameters["withVerified"]?.toBoolean() ?: true
                    val includedResources = call.request.queryParameters["include"]?.split(",")

                    val drugData = drugController.getAllByCreator(creatorId, withVerified, includedResources)

                    call.respond(drugData)
                } catch (e: Exception) {
                    val statusCode = when (e) {
                        is IllegalArgumentException, is NumberFormatException -> HttpStatusCode.BadRequest
                        else -> throw e;
                    }
                    call.respond(statusCode)
                }
            }
        }

        put("/agendaentry/{id}") {
            val agendaController = AgendaController()
            val agendaId: Int = call.parameters["id"].toString().toInt()
            val agenda = call.receive<Agenda>()
            agendaController.updateAgendaEntry(agendaId, agenda)
            call.respond(agenda)
        }

        post("/agendaentry") {
            val agendaController = AgendaController()
            val AgendaFDTO = call.receive<AgendaFDTO>()

            agendaController.createAgendaEntry(AgendaFDTO)
            call.respond(AgendaFDTO)
        }
        get("/agendaentries/{creatorId}") {
            val agendaController = AgendaController()
            val creatorId = call.parameters["creatorId"].toString().toInt()
            val includedResources: List<String>? = call.request.queryParameters["include"]?.split(",")
            val AgendaEntries = agendaController.getAgendaEntriesByCreator(creatorId, includedResources)

            call.respond(AgendaEntries)
        }

        delete("/agendaentry/{id}") {
            val agendaController = AgendaController()
            val agendaId: Int = call.parameters["id"].toString().toInt()
            agendaController.deleteAgendaEntry(agendaId)
            call.respond(HttpStatusCode.OK)
        }

        post("/login") {
            val userData = call.receive<User>()
            var userID: Int = 0
            transaction {
                UserDAO.select { (UserDAO.email eq userData.email.toString()) and (UserDAO.password eq userData.password.toString()) }
                    .map {
                        userID = it[UserDAO.id]
                    }
            }
            call.respond(userID)
        }

        post("/users") {
            try {
                val userController = UserController()

                val userFDTO = call.receive<UserFDTO>()
                val authData = userController.createNew(userFDTO)

                call.respond(HttpStatusCode.Created, authData)
            } catch (e: Exception) {
                var statusCode = HttpStatusCode.InternalServerError
                var message = "Something went wrong with the server."

                when (e) {
                    is UnrecognizedPropertyException, is MissingKotlinParameterException -> {
                        statusCode = HttpStatusCode.UnprocessableEntity
                        message = "Body parameters doesn't suffice the specifications."
                    }
                    is UserEmailExists, is IllegalArgumentException -> {
                        statusCode = HttpStatusCode.UnprocessableEntity
                        e.message?.let { message = it }
                    }
                }

                call.respond(statusCode, message)
            }
        }
//        val userController = UserController()
//        val drugController = DrugController()
//        val drugComponentController = DrugComponentController()


//        get("/creators/{creatorId}/drugs") {
//            val drugController = DrugController()
//
//            val includedResources: String? = call.request.queryParameters["include"]
//            val withVerified: Boolean = call.request.queryParameters["withVerified"]?.toBoolean() ?: false
//            val includedResourcesParts = includedResources?.let { includedResources.split(",") }
//
//            val creatorID = 2
//
//            val drugData = drugController.getAll(includedResourcesParts, creatorID)
//            //var drugComponentData: ArrayList<ArrayList<DrugComponent>> = ArrayList()
//
//            call.respond(drugData)

//            drugData.forEach() {
//                drugComponentData.add((drugData.indexOf(it)), drugComponentController.getAll(it.id))
//            }
//            drugData.forEach() {
//                if (drugComponentData[(drugData.indexOf(it))].isEmpty()) {
//                    drugComponentData.add((drugData.indexOf(it)), DrugComponent(0, 0, 0.00, 0, "null"))
//                }
//            }
//
//            val drugJsonString = json {
//                obj("data" to drugData.map {
//                    obj(
//                            "type" to "drugs",
//                            "id" to it.id,
//                            "attributes" to obj(
//                                    "name" to it.name,
//                                    "thumbnailURL" to it.thumbnailURL
//                            ),
//                            "relationships" to obj(
//                                    "brand" to obj(
//                                            "type" to "brands",
//                                            "id" to it.brandID
//                                    ),
//                                    "source" to obj(
//                                            "type" to "source",
//                                            "id" to it.sourceID
//                                    ),
//                                    "creator" to obj(
//                                            "type" to "users",
//                                            "id" to it.creatorID
//                                    ),
//                                    "components" to drugComponentData[(drugData.indexOf(it))].map {
//                                        obj(
//                                                "type" to "drugComponents",
//                                                "id" to it.componentID
//                                        )
//                                    }
//                            ))
//
//
//                })
//            }
//            call.respond(drugJsonString)


//        }


//        put("/user/{id}") {
//            val ID: Int = call.parameters["ID"].toString().toInt()
//            val userDTO = call.receive<UserDTO>()
//            userController.update(userDTO, ID)
//            call.respond(HttpStatusCode.OK)
//        }
//        put("/drug/{id}") {
//            val ID: Int = call.parameters["ID"].toString().toInt()
//            val drugDTO = call.receive<DrugDTO>()
//            drugController.update(drugDTO, ID)
//            call.respond(HttpStatusCode.OK)
//        }

//        delete("/user/{id}") {
//            val ID: Int = call.parameters["ID"].toString().toInt()
//            userController.delete(ID)
//            call.respond(HttpStatusCode.OK)
//        }

//        delete("/drug/{ID}") {
//            val ID: Int = call.parameters["ID"].toString().toInt()
//            drugController.delete(ID)
//            call.respond(HttpStatusCode.OK)
//        }
    }
}


//private fun <E> ArrayList<E>.add(index: Int, element: DrugComponent) {