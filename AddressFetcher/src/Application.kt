package com.vayana

import arrow.core.Either
import arrow.core.extensions.fx
import de.jupf.staticlog.Log
import io.ktor.application.Application
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    try{
        //Establishing db connection by using Either<A,B> & flatmap{}
//        val dbConRes1: Either<Fault, Database> = getSqlUrl().flatMap { SqlUrl ->
//            getSqlDriver().flatMap { SqlDriver ->
//                getSqlUserName().flatMap { SqlUserName ->
//                    getSqlPassword().flatMap { SqlPassword ->
//                        val dbInstance = Database.connect(SqlUrl, driver = SqlDriver, user = SqlUserName, password = SqlPassword)
//                        dbInstance.right()
//                    }
//                }
//            }
//        }
        //Establishing db connection by using Either<A,B> & bind()
        val dbConRes2 = Either.fx<Fault, Database> {
            val sqlUrl = getSqlUrl().bind()
            val sqlDriver = getSqlDriver().bind()
            val sqlUserName = getSqlUserName().bind()
            val sqlPassword = getSqlPassword().bind()
            Database.connect(sqlUrl, driver = sqlDriver, user = sqlUserName, password = sqlPassword)
        }
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
            url0,
            driver = driver0,
            user = user0,
            password = password0
        )
    }
//this is to handle all the routes, each protocol has its own function
    routing {
        this.getUsers()
        this.getUserById()
        this.post()
        this.contentNegotiation()
        this.helloWorld()
    }

}



