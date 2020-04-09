package com.vayana

import arrow.core.Either
import arrow.core.Option
import arrow.core.left
import arrow.core.right
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class User(val id: Int?, val name:String, val age:Int, val address:String)

object Users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val age: Column<Int> = integer("age")
    val address: Column<String> = varchar("address", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_USER_IDs")

    fun toUser(row: ResultRow): Option<User> =
        Option(User(
            id = row[Users.id],
            name = row[Users.name],
            age = row[Users.age],
            address = row[Users.address]
        ))

}

fun getAllUsers(): Either<Fault, List<Option<User>>> =
        try {
            transaction {
                Users.selectAll().map{Users.toUser(it)}
            }.right()
        }
        catch(ex: Exception){
            Fault(failedToFetchUsers).left()
        }


