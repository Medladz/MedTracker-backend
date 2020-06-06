package com.medtracker

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import io.ktor.locations.*
import com.beust.klaxon.*
import com.medtracker.controllers.DrugComponentController
import com.medtracker.controllers.DrugController
import com.medtracker.controllers.UserController
import com.medtracker.models.DrugComponent
import com.medtracker.models.DrugDTO
import com.medtracker.models.UserDTO
import kotlin.text.*
import java.util.ArrayList


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

    install(Locations)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    initDB()

    routing {
        get<Creators.Drugs> { drugs ->
            val drugController = DrugController()

            val includedResources =  drugs.include.split(",")

            val drugData = drugController.getAllByCreator( drugs.parent.creatorId, drugs.withVerified, includedResources)

            call.respond(drugData)
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
//            // @todo creatorID uit url halen
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
//        post("/drug") {
//            val drugDTO = call.receive<DrugDTO>()
//            drugController.insert(drugDTO)
//            call.respond(drugDTO)
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
//
//}

@Location("/creators/{creatorId}") data class Creators(val creatorId: Int) {
    @Location("/drugs") data class Drugs(val parent: Creators, val withVerified: Boolean = true, val include: String = "")
}