package com.vayana

import io.ktor.application.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import de.jupf.staticlog.Log

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    try{
        Database.connect("$url", driver = "$driver", user = "$user", password = "$password")
        transaction {
            SchemaUtils.create(Users)
        }
    }
    catch (e:Exception){
        Log.warn("invalid credentials")
    }
    finally {
        //if the schema does'nt exist, it ll create one to perform the operations
        Database.connect(
            "jdbc:mysql://localhost:3306/userDetails?createDatabaseIfNotExist=true",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = "root"
        )
    }

    routing {
        this.getUsers()
        this.getUserById()
        this.post()
        this.contentNegotiation()
        this.helloWorld()
    }

}



