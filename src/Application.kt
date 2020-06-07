package com.medtracker

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import com.beust.klaxon.*
import com.medtracker.controllers.AgendaController
import com.medtracker.controllers.DrugController
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import com.medtracker.services.dto.AgendaFDTO
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

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    initDB()

    routing {
        get("/creators/{creatorId}/drugs") {
            try {
                val drugController = DrugController()

                val creatorId = call.parameters["creatorId"]?.toInt()
                val withVerified = call.request.queryParameters["withVerified"]?.toBoolean() ?: true
                val includedResources = call.request.queryParameters["include"]?.split(",")

                if (creatorId === null) {
                    throw IllegalArgumentException()
                }

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

        post("/agendaentry"){
            val agendaController = AgendaController()
            val AgendaFDTO = call.receive<AgendaFDTO>()

            agendaController.createAgendaEntry(AgendaFDTO)
            call.respond(AgendaFDTO)
        }

//        val userController = UserController()
//        val drugController = DrugController()
//        val drugComponentController = DrugComponentController()

//        get("/users") {
//            val userData = userController.getAll()
//
//            call.respond(userData)
//
//        }

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


//        post("/user") {
//            val userDTO = call.receive<UserDTO>()
//            userController.insert(userDTO)
//            call.respond(userDTO)
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