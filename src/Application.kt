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
import com.medtracker.services.JWTAuth
import java.lang.Exception
import java.lang.IllegalArgumentException
import com.medtracker.services.dto.AgendaFDTO
import com.medtracker.services.dto.LoginFDTO
import com.medtracker.services.dto.UserFDTO
import com.medtracker.utilities.UnauthorizedException
import com.medtracker.utilities.BadRequestException
import com.medtracker.utilities.InternalServerErrorException
import com.medtracker.utilities.UnprocessableEntityException
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.jwt
import io.ktor.http.HttpStatusCode
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

    install(StatusPages) {
        exception<UnrecognizedPropertyException> {
            call.respond(HttpStatusCode.UnprocessableEntity, "Body parameters didn't meet the requirements.")
        }

        exception<MissingKotlinParameterException> {
            call.respond(HttpStatusCode.UnprocessableEntity, "Body parameters didn't meet the requirements.")
        }

        exception<UnauthorizedException> {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials.")
        }

        exception<UnprocessableEntityException> { exception ->
            call.respond(HttpStatusCode.UnprocessableEntity, exception.message ?: "Request is invalid.")
        }

        exception<BadRequestException> { exception ->
            call.respond(HttpStatusCode.BadRequest, exception.message ?: "Request is invalid.")
        }

        exception<Exception> {
            call.respond(HttpStatusCode.InternalServerError, "Something went wrong with the server.")
        }
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
        route("/users") {
            post {
                val userController = UserController()

                val userFDTO = call.receive<UserFDTO>()
                val authData = userController.createNew(userFDTO)

                call.respond(HttpStatusCode.Created, authData)
            }

            post("/login") {
                val userController = UserController()

                val loginFDTO = call.receive<LoginFDTO>()
                val authData = userController.login(loginFDTO)

                call.respond(authData)
            }
        }

        authenticate {
            get("/drugs") {
                val drugController = DrugController()

                val user = call.authentication.principal<User>()!!
                val creatorId = user.id!!
                val withVerified = call.parameters["withVerified"]?.toBoolean() ?: true
                val includedResources = call.parameters["include"]?.split(",")

                val drugData = drugController.getAllByCreator(creatorId, withVerified, includedResources)

                call.respond(drugData)
            }

            route("/agendaEntries") {
                post {
                    val agendaController = AgendaController()
                    val agendaFDTO = call.receive<AgendaFDTO>()

                    agendaController.createAgendaEntry(agendaFDTO)

                    call.respond(HttpStatusCode.Created, mapOf("data" to "Agenda has been created."))
                }

                get {
                    val agendaController = AgendaController()

                    val user = call.authentication.principal<User>()!!
                    val creatorId = user.id!!
                    val includedResources = call.parameters["include"]?.split(",")
                    val agendaEntries = agendaController.getAgendaEntriesByCreator(creatorId, includedResources)

                    call.respond(agendaEntries)
                }

                delete("/{id}") {
                    val agendaController = AgendaController()

                    val agendaId: Int = call.parameters["id"]?.toInt() ?: throw InternalServerErrorException()

                    agendaController.deleteAgendaEntry(agendaId)

                    call.respond(mapOf("data" to "Agenda has been deleted."))
                }

                put("/{id}"){
                    val agendaController = AgendaController()

                    val agendaId: Int = call.parameters["id"]?.toInt() ?: throw InternalServerErrorException()
                    val agenda = call.receive<Agenda>()

                    agendaController.updateAgendaEntry(agendaId, agenda)

                    call.respond(mapOf("data" to "Agenda has been modified."))
                }
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