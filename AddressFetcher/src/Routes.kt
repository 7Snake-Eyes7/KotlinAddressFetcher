package com.vayana


import arrow.core.Either
import arrow.core.Option
import de.jupf.staticlog.Log
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

const val path = "/user/"

fun Routing.getUsers(){
    get(path){
        try{
            val users: Either<Exception, List<Option<User>>> = Either.Right(transaction {
                Users.selectAll().map{Users.toUser(it)}
            })
            call.respond(users)
            Log.info("The output is $users")

        }
        catch (e:Exception){
            Either.Left("Internal server error")
            Log.warn("something went wrong")
        }

    }

}

fun Routing.getUserById(){
    get("$path{id}"){

        try{
            val id: Int = call.parameters["id"]!!.toInt()
            val users: Either<Nothing, List<Option<User>>> = Either.Right(transaction {
                Users.select { Users.id eq id}.map{Users.toUser(it)}
            })
            call.respond(users)
            Log.info("The output is $users")
        }
        catch(e:Exception){
            Either.Left("Internal server error")
            Log.warn("something went wrong")
        }

    }

}


fun Routing.post(){
    post(path){

        try{
            val user: User = call.receive()
            transaction {
                Users.insert {
                    it[name] = user.name
                    it[age] = user.age
                    it[address] = user.address
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


