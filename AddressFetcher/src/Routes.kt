//package com.vayana
//
//import io.ktor.application.call
//import io.ktor.application.install
//import io.ktor.features.ContentNegotiation
//import io.ktor.gson.gson
//import io.ktor.request.receive
//import io.ktor.response.respond
//import io.ktor.routing.*
//import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
//import org.jetbrains.exposed.sql.insert
//import org.jetbrains.exposed.sql.select
//import org.jetbrains.exposed.sql.selectAll
//import org.jetbrains.exposed.sql.transactions.transaction
//
//
//fun Routing.getUsers(){
//    get("/"){
//        val users = transaction {
//            Users.selectAll().map{Users.toUser(it)}
//        }
//        call.respond(users)
//    }
//
//}
//
//fun Routing.getUserById(){
//    get("/{id}"){
//
//        val id = call.parameters["id"]!!.toInt()
//        val users = transaction {
//            Users.select { Users.id eq id}.map{Users.toUser(it)}
//        }
//        call.respond(users)
//    }
//
//
//}
//
//
//fun Routing.post(){
//    post("/"){
//        val user = call.receive<User>()
//        transaction {
//            Users.insert {
//                it[Users.name] = user.name
//                it[Users.age] = user.age
//                it[Users.address] = user.address
//            }
//        }
//        call.respond(user)
//    }
//
//}
//
//
//fun Routing.contentNegotiation(){
//    install(ContentNegotiation){
//
//        gson {
//
//        }
//    }
//
//}