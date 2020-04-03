package com.vayana

import com.vayana.Users.name
import io.ktor.application.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class User(val id: Int? =null, val name:String, val age:Int, val address:String)

object Users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val age: Column<Int> = integer("age")
    val address: Column<String> = varchar("address", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_USER_IDs")

    fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            name = row[Users.name],
            age = row[Users.age],
            address = row[Users.address]
            )
}


fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {


    Database.connect("jdbc:mysql://localhost:3306/userDetails?createDatabaseIfNotExist=true", driver = "com.mysql.jdbc.Driver",
        user = "root", password = "root")
    transaction {
        SchemaUtils.create(Users)

        Users.insert {
            it[Users.name] = "rak"
            it[Users.age] = 25
            it[Users.address] = "bengaluru"
        }
    }

    install(Routing){
        route("/user"){

            get("/"){
                val users = transaction {
                    Users.selectAll().map{Users.toUser(it)}
                }
                call.respond(users)
            }

            get("/{id}"){

                val id = call.parameters["id"]!!.toInt()
                val users = transaction {
                    Users.select { Users.id eq id}.map{Users.toUser(it)}
                }
                call.respond(users)
            }

            post("/"){
                val user = call.receive<User>()
                transaction {
                    Users.insert {
                        it[Users.name] = user.name
                        it[Users.age] = user.age
                        it[Users.address] = user.address
                    }
                }
                call.respond(user)
            }
        }
    }
}


