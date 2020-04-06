package com.vayana

import com.vayana.Users.name
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
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
    }

    routing{
        this.getUsers()
        this.getUserById()
        this.post()
        this.contentNegotiation()
    }
}



