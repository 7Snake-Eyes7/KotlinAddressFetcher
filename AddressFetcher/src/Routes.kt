package com.vayana


import arrow.core.Option
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception
import de.jupf.staticlog.Log

const val path = "/user/"

fun Routing.getUsers(){
    get(path){
        try{
            val users = transaction {
                Users.selectAll().map{Users.toUser(it)}
            }
            call.respond(users)
            Log.info("The output is $users")

        }
        catch (e:Exception){
            Log.warn("something went wrong")
        }

    }

}

fun Routing.getUserById(){
    get("$path{id}"){

        try{
            val id: Int = call.parameters["id"]!!.toInt()
            val users = transaction {
                Users.select { Users.id eq id}.map{Users.toUser(it)}
            }
            call.respond(users)
            Log.info("The output is $users")
        }
        catch(e:Exception){
            Log.warn("something went wrong")
        }

    }


}


fun Routing.post(){
    post(path){

        try{
            val user = call.receive<User>()
            transaction {
                Users.insert {
                    it[Users.name] = user.name
                    it[Users.age] = user.age
                    it[Users.address] = user.address
                }
            }
            call.respond(user)
            Log.info("The output is $user")
        }
        catch(e:Exception){
            Log.warn("something went wrong")
        }
    }

}


fun Routing.contentNegotiation(){
    install(ContentNegotiation){

        gson {

        }
    }

}

fun Routing.helloWorld(){
    get("$path/hello"){
        try{
            call.respond("hello world")
            Log.info("hello world")

        }
        catch (e:Exception){
            Log.warn("something went wrong")
        }

    }

}

